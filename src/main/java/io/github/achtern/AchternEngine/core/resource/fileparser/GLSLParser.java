package io.github.achtern.AchternEngine.core.resource.fileparser;

import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.GLSLScript;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.GLSLStruct;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom GLSL pre-parser.
 * Currently supporting #include directives and various build in parsers
 */
public class GLSLParser extends VariableBasedLanguageParser implements LineBasedParser {

    public static final Logger LOGGER = LoggerFactory.getLogger(GLSLParser.class);

    /**
     * CUSTOM TOKEN. NOT PART OF THE GLSL LANGUAGE!
     * #include MUST be first statement in the line
     * Any two characters after that are allowed.
     * The 3rd character after '#include' has to be part
     * of the filename. The rest of the line will get treated
     * as the filename, BUT the last character!
     * (Trailing whitespaces will get trimmed automatically!)
     *
     * It is advised to stick with either '<' or '"' as delimiter
     * because the parser might switch to regex based parsing.
     *
     * Example:
     * <code>
     *     #include "file.ext"
     *     // or
     *     #include <file.ext>
     *     // or even
     *     #include $file.ext$
     *
     *     // You could also use two spaces..
     * </code>
     */
    public static final String CUSTOM_TOKEN_INCLUDE = "#include";

    /**
     * The uniform token used in the GLSL language
     * (uniform type name)
     */
    public static final String TOKEN_UNIFORM = "uniform";

    /**
     * The attribute token used in the GLSL language
     * (attribute type name)
     */
    public static final String TOKEN_ATTRIBUTE = "attribute";

    /**
     * The struct token used in the GLSL language
     * (struct name {...})
     */
    public static final String TOKEN_STRUCT = "struct";

    /**
     * The character used to end a line or a statement.
     * <code>
     *  vec3 name = vec3(...);
     * </code>
     */
    public static final String TOKEN_END_STATEMENT = ";";

    /**
     * The parser will look into this directory, in order to include
     * files.
     * Vertex Shader: shaders/shader.gvs
     * Random Header File: shaders/include/head.gh
     *
     * Vertex Source:
     *
     * #include "head.gh"
     *
     * ----
     *
     * This will work!
     */
    public static final String INCLUDE_DIRECTORY = "include/";

    /**
     * @see io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser#parse(String)
     */
    @Override
    public String parse(String line) throws IOException {
        line = line.trim();

        if (line.startsWith(CUSTOM_TOKEN_INCLUDE)) {
            /*
            Looks like this:
            #include "file.ext"
            or
            #include "file.ext"
             */
            String filename = line.substring(CUSTOM_TOKEN_INCLUDE.length() + 2, line.length() - 1);

            line = ResourceLoader.getShader(INCLUDE_DIRECTORY + filename);


        }


        return line;
    }

    public List<GLSLStruct> getUniformStructs(String text) {

        List<GLSLStruct> structs = new ArrayList<GLSLStruct>();

        int startLoc = text.indexOf(TOKEN_STRUCT);
        boolean hasMore = true;

        if (startLoc == -1) {
            // No struct found.
            hasMore = false;
        }

        while (hasMore) {

            // Should be 0 in most cases...
            // (when you do not seperate the structs with white space stuff)
            startLoc = text.indexOf(TOKEN_STRUCT);

            // remove previous trash
            // I know methods after this still be part of the text... But who cares?!
            text = text.substring(startLoc);
            startLoc = text.indexOf(TOKEN_STRUCT);

            // Start at the position of "struct" and end at "};"
            String struct = text.substring(startLoc, text.indexOf("}" + TOKEN_END_STATEMENT));
            String[] structParts = struct.split("\\{");

            String head = structParts[0].trim();
            String body = structParts[1];


            // Now we need to convert space based indention to tab based.
            // This way we can use the tab character as token.
            body = body
                    .replace("    ", "\t") // 4 spaces
                    .replace("   ", "\t") // r spaces
                    .replace("  ", "\t") // 2 spaces
                    .replace("\n", "\t");// Line Breaks

            // And add a tab to the start
            if (!body.startsWith("\t")) {
                body = "\t" + body;
            }

            // The name of the struct "struct Foo" => "Foo"
            String name = head.substring(TOKEN_STRUCT.length() + 1, head.length());
            // Get the struct member variables
            List<Variable> variables = getVariables(body, "\t");

            structs.add(new GLSLStruct(name, variables));

            // Removed already parsed code.
            text = text.substring(struct.length() + 1 + TOKEN_END_STATEMENT.length());

            if (!text.contains(TOKEN_STRUCT)) {
                hasMore = false;
            }
        }

        return structs;
    }

    public GLSLScript process(GLSLScript script) {

        List<Variable> uniforms = getVariables(script.getSource(), TOKEN_UNIFORM);


        script.setStructs(getUniformStructs(script.getSource()));

        script.setAttributes(getAttributes(script.getSource()));

        script.setUniforms(getUniforms(script.getSource(), uniforms, script.getStructs()));

        script.setProcessed(true);

        return script;

    }

    public List<Uniform> getUniforms(String text, List<Variable> uniforms, List<GLSLStruct> structs) {
        List<Uniform> finalUniforms = new ArrayList<Uniform>();

        if (LOGGER.isTraceEnabled()) {
            StringBuilder sb = new StringBuilder();
            for (Variable v : uniforms) {
                sb.append(v.getName());
                sb.append(";");
            }

            LOGGER.trace("Found the following uniforms: <{}>", sb.toString());
        }

        List<Variable> primitives = filterPrimitives(uniforms);

        // We do not need a complete list anymore...
        uniforms.removeAll(primitives);

        // Add all primitives
        for (Variable v : primitives) {
            finalUniforms.add(new Uniform(v));
        }

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Added the following primitve uniforms:");
            for (Variable v : primitives) {
                LOGGER.trace("    <{}>{}<{}>", v.getType(), v.getName(), v.getType());
            }
        }

        if (LOGGER.isTraceEnabled()) {
            printStructs(structs);
        }

        // Flatten structs to uniform strings
        // uniform Foo fooThing;
        // struct Foo
        // {
        //  float x;
        //  Bar b;
        // };
        // struct Bar
        // {
        //  float y;
        //  float z;
        // };
        //
        // yields:
        // (List of):
        // - fooThing.x
        // - fooThing.b.y
        // - fooThing.b.z

        boolean struct = false;
        for (Variable v : uniforms) {
            LOGGER.trace("Trying to match ({} {}) against struct list", v.getType(), v.getName());
            GLSLStruct match = findNameInList(v.getType(), structs);

            // If it is a known struct, expand it, otherwise just add it
            if (match != null) {
                LOGGER.trace("Found a match, now expanding");
                for (String expanded : expandUniform(v, match, structs)) {
                    finalUniforms.add(new Uniform(match.getName(), expanded));
                }
            } else {
                LOGGER.debug("Did not found struct specified by uniform type! ({} {})", v.getType(), v.getName());
                finalUniforms.add(new Uniform(v));
            }
        }


        return finalUniforms;
    }

    public List<Variable> getAttributes(String text) {
        return getVariables(text, TOKEN_ATTRIBUTE);
    }

    public List<String> getVariableNames(String text, final String token) {
        List<String> names = new ArrayList<String>();

        for (Variable v : getVariables(text, token)) {
            names.add(v.getName());
        }

        return names;

    }

    public static List<Variable> getVariables(String text, final String token) {

        List<Variable> vars = new ArrayList<Variable>();

        int startLoc = text.indexOf(token);

        boolean hasMore = true;

        if (startLoc == -1) {
            // No variables found.
            hasMore = false;
        }


        while (hasMore) {

            // Should be 0 in most cases...
            // (when you do not seperate the structs with white space stuff)
            startLoc = text.indexOf(token);

            // remove previous trash
            // I know methods after this still be part of the text... But who cares?!
            text = text.substring(startLoc);
            startLoc = text.indexOf(token);

            if (text.length() < 2) {
                hasMore = false;
                continue;
            }

            // Usally as:
            // TOKEN TYPE NAME;
            String variable = text.substring(startLoc, text.indexOf(TOKEN_END_STATEMENT));

//            LOGGER.trace("VARIABLE STRING: <{}>", variable);

            String[] parts = variable.split("\\s+");

            String type = parts[1];
            String name = parts[2];

            vars.add(new Variable(type, name));

            LOGGER.trace("<{}>{}</{}>", type, name, type);

            // Removed already parsed code.
            text = text.substring(variable.length() + TOKEN_END_STATEMENT.length());

//            LOGGER.trace("Shader text is now: <{}>", text);

            if (!text.contains(token)) {
                LOGGER.trace("Finished parsing shader text.");
                hasMore = false;
            }


        }

        return vars;
    }

    public static List<String> expandUniform(Variable uniform, GLSLStruct main, List<GLSLStruct> structs) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Job: Expand <uniform {} {}>", uniform.getType(), uniform.getName());
            List<String> r = expendVariable(uniform, main, structs);
            LOGGER.trace("uniform {} {} got expanded to:", uniform.getType(), uniform.getName());
            for (String s : r) {
                LOGGER.trace(s);
            }

            return r;
        }

        return expendVariable(uniform, main, structs);
    }

    public static List<String> expendVariable(Variable variable, GLSLStruct main, List<GLSLStruct> structs) {
        List<String> r = new ArrayList<String>();

        for (Variable member : main.getMembers()) {
            LOGGER.trace("Working on member:{} {}", member.getType(), member.getName());
            if (isPrimitive(member)) {
                LOGGER.trace("Just added this member. Next!");
                r.add(member.getName());
            } else {
                GLSLStruct s = findNameInList(member.getType(), structs);

                // If it is a known struct, added, otherwise the name of the variable ought to be enough
                if (s != null) {
                    LOGGER.trace("Making recursiv call to expand this member {} {} with struct provided: {}", member.getType(), member.getName(), s.getName());
                    r.addAll(expendVariable(member, s, structs));
                } else {
                    LOGGER.trace("Missing struct for variable {} {}", variable.getType(), variable.getName());
                    r.add(variable.getName() + "." + member.getName());
                }
            }
        }

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("variable {} {} got expanded to:", variable.getType(), variable.getName());
            for (String s : r) {
                LOGGER.trace(s);
            }
        }

        // Add the main variable to all.
        List<String> finalList = new ArrayList<String>(r.size());
        for (String expanded : r) {
            finalList.add(variable.getName() + "." + expanded);
        }

        return finalList;
    }

    /**
     * Just iterates over the list and adds primitves to a new list
     * @param variables All variables
     * @return The new list with all primitves from the variables list
     */
    protected static List<Variable> filterPrimitives(List<Variable> variables) {

        List<Variable> primitives = new ArrayList<Variable>();

        for (Variable v : variables) {

            if (isPrimitive(v)) {
                primitives.add(v);
            }
        }

        return primitives;

    }

    /**
     * Check if the given variable is primitiv.
     *
     * This is a very cheap detection, but should work in most cases
     * if not, than someone is not following coding conventions
     * and I hate this person anyway... So who cares?!
     * (No really, I'll fix this someday)
     *
     * @param v The variable to check
     * @return Whether the variable is a primitive
     */
    protected static boolean isPrimitive(Variable v) {
        boolean upperCase = Character.isUpperCase(v.getType().trim().charAt(0));
//        LOGGER.trace("{} is non-primitiv: {}", v.getType(), upperCase);
        return !upperCase;
    }

    /**
     * Just iterates over the list and returns the first one with a matching name.
     * Nothing special...
     * @param name The name to search for
     * @param structs A list of GLSLStructs
     * @return The struct with a matching name | null if not found
     */
    protected static GLSLStruct findNameInList(String name, List<GLSLStruct> structs) {
        for (GLSLStruct s : structs) {
            if (s.getName().equals(name)) {
                return s;
            }
        }

        return null;
    }

    /**
     * Prints a list of GLSLStruct s in an XML like format:
     * (Only if trace logging is enabled!)
     * <code>
     *     Struct Foo
     *     {
     *         float x;
     *         vec4 y;
     *     };
     *
     *     will yield
     *     <Foo>
     *         <float>x</float>
     *         <vec3>y</vec3>
     *     </Foo>
     * </code>
     * @param structs A list of GLSLStructs to print
     */
    protected static void printStructs(List<GLSLStruct> structs) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Printing all structs in list!");
            for (GLSLStruct struct : structs) {

                    LOGGER.trace("<{}>", struct.getName());

                    for (Variable v : struct.getMembers()) {
                        LOGGER.trace("\t<{}>{}</{}>", v.getType(), v.getName(), v.getType());
                    }

                    LOGGER.trace("</{}>", struct.getName());
            }
            LOGGER.trace("Done.");
        }
    }
}

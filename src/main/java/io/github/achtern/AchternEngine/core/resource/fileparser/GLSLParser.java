/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.achtern.AchternEngine.core.resource.fileparser;

import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.GLSLScript;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.GLSLStruct;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * CUSTOM TOKEN. NOT PART OF THE GLSL LANGUAGE!
     * #import MUST be first statement in the line
     * Is at this point the same as include, but
     * behaviour might change soon. Should be used
     * for .glib files!
     * @see #CUSTOM_TOKEN_INCLUDE
     */
    public static final String CUSTOM_TOKEN_IMPORT = "#import";

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

    public static final String TOKEN_SINGLE_LINE_COMMENT = "//";

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
     * GLSLParser currently only works on include statements
     * @see io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser#parse(String)
     */
    @Override
    public String parse(String line) throws Exception {
        line = line.trim();

        if (line.contains(TOKEN_SINGLE_LINE_COMMENT)) {
            line = line.substring(0, line.indexOf(TOKEN_SINGLE_LINE_COMMENT));
        }

        if (line.startsWith(CUSTOM_TOKEN_INCLUDE)) {
            /*
            Looks like this:
            #include "file.ext"
            or
            #include "file.ext"
             */
            String filename = line.substring(CUSTOM_TOKEN_INCLUDE.length() + 2, line.length() - 1);

            line = ResourceLoader.getShader(INCLUDE_DIRECTORY + filename);


        } else if (line.startsWith(CUSTOM_TOKEN_IMPORT)) {
            /*
            Looks like this:
            #include "file.ext"
            or
            #include "file.ext"
             */
            String filename = line.substring(CUSTOM_TOKEN_IMPORT.length() + 2, line.length() - 1);

            line = ResourceLoader.getShader(INCLUDE_DIRECTORY + filename);


        }


        return line;
    }

    /**
     * Parses all structs out of the supplied shader source
     * @param text shader source
     * @return List of found GLSLStructs
     */
    public List<GLSLStruct> getStructs(String text) {

        // All structs will get stored in this List.
        List<GLSLStruct> structs = new ArrayList<GLSLStruct>();

        // First struct in code
        int startLoc = text.indexOf(TOKEN_STRUCT);

        // If we have a start position, we must have at least one struct!
        boolean hasMore = startLoc != -1;


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

            // create the case class and add it to the return list
            structs.add(new GLSLStruct(name, variables));

            // Removed already parsed code.
            text = text.substring(struct.length() + 1 + TOKEN_END_STATEMENT.length());

            // check if there are any more structs left.
            if (!text.contains(TOKEN_STRUCT)) {
                hasMore = false;
            }
        }

        return structs;
    }

    /**
     * Processes a {@link GLSLScript}.
     * Adds uniforms, expanded uniforms, attributes and all structs in the source.
     * Marks script as processed via {@link GLSLScript#setProcessed(boolean)}
     * @param script The script to process
     * @return The same object as script
     */
    public GLSLScript process(GLSLScript script) {

        // Grab all uniforms as variables, to have the type as well.
        List<Variable> uniforms = getVariables(script.getSource(), TOKEN_UNIFORM);

        // set as uniforms
        script.setUniformsFromVariable(uniforms);

        // inject all structs
        script.setStructs(getStructs(script.getSource()));

        // inject all attributes
        script.setAttributes(getAttributes(script.getSource()));

        // now expand the uniforms for adding later on.
        script.setExpandedUniforms(getExpandedUniforms(script.getSource(), uniforms, script.getStructs()));

        // marks as processed
        script.setProcessed(true);

        return script;

    }

    /**
     * Expands all uniforms for adding to the shader programm
     * @param text shader source
     * @param uniforms List of uniforms
     * @param structs A list of GLSLStructs
     * @return List of expanded Uniforms
     */
    public List<Uniform> getExpandedUniforms(String text, List<Variable> uniforms, List<GLSLStruct> structs) {
        List<Uniform> finalUniforms = new ArrayList<Uniform>();

        if (LOGGER.isTraceEnabled()) {
            StringBuilder sb = new StringBuilder();
            for (Variable v : uniforms) {
                sb.append(v.getName());
                sb.append(";");
            }

            LOGGER.trace("Found the following uniforms: <{}>", sb.toString());
        }

        // If the uniform is a primitive and not a custom struct,
        // we do not need to expand it.
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

        // Cycle thru all uniforms
        for (Variable v : uniforms) {
            LOGGER.trace("Trying to match ({} {}) against struct list", v.getType(), v.getName());

            // Check if the struct has been parsed already (=> exists)
            GLSLStruct match = findNameInList(v.getType(), structs);

            // If it is a known struct, expand it, otherwise just add it
            if (match != null) {
                LOGGER.trace("Found a match, now expanding");
                // Now expand this uniform. expandUniform is recursive.
                // This means it will expand nested structs as well!
                for (String expanded : expandUniform(v, match, structs)) {
                    finalUniforms.add(new Uniform(match.getName(), expanded));
                }
            } else {
                LOGGER.debug("Did not found struct specified by uniform type! ({} {})", v.getType(), v.getName());
                // otherwise just add the unexpanded uniform
                finalUniforms.add(new Uniform(v));
            }
        }


        return finalUniforms;
    }

    /**
     * Just a wrapper for getVariables(text, TOKEN_ATTRIBUTE);
     * @param text Shader source
     * @return List of attribute variables
     */
    public List<Variable> getAttributes(String text) {
        return getVariables(text, TOKEN_ATTRIBUTE);
    }

    /**
     * Scans through the shader source and extracts variables which
     * are "flagged" with a specific token.
     * For example:
     *
     * source = """
     * TOKEN type identifyer;
     * ANOTHERTOKEN type identifyer2;
     * TOKEN anothertype identifyer3;
     * """
     *
     * A call to this method with the arguments
     * - source
     * - "TOKEN"
     *
     * will return a List of 2 Variable objects.
     * Variable{name="identifyer", type="type"}
     * Variable{name="identifyer3", type="anothertype"}
     *
     * @param text Shader source
     * @param token The token which will be used to filter statements of variables.
     * @return List of variables
     */
    public static List<Variable> getVariables(String text, final String token) {

        // Final List will be returned
        final List<Variable> vars = new ArrayList<Variable>();

        // Find first position of token
        int startLoc = text.indexOf(token);

        // if we have a start position, we also have at least one variable
        boolean hasMore = startLoc != -1;



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

            // Split by whitespace
            String[] parts = variable.split("\\s+");

            // parts[0] would be the token again.
            // Lets check this
            assert parts[0].trim().equalsIgnoreCase(token.trim());

            String type = parts[1];
            String name = parts[2];

            // Create data class and add it to the return List
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

    /**
     * Expand a uniform
     * If a uniform's type is a custom struct, OpenGL
     * expects you to expand it.
     *
     * This method is a wrapper for expandVariable,
     * but with debug statements (if TRACE is enabled)
     *
     * Example:
     *
     * struct Foo {
     *     float var;
     *     float another;
     * }
     *
     * uniform Foo fooU;
     *
     * fooU should be expanded to
     * - fooU.var
     * - fooU.another
     *
     * @param uniform The uniform to expand
     * @param main The main struct (top-level) of the uniform
     * @param structs A list of GLSLStructs
     * @return List of expanded uniforms
     */
    public static List<String> expandUniform(Variable uniform, GLSLStruct main, List<GLSLStruct> structs) {
        // If trace is enabled we enter a slightly more costly return,
        // but equal in result
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

    /**
     * Expand a variable
     * If a uniform's type is a custom struct, OpenGL
     * expects you to expand it.
     *
     * Example:
     *
     * struct Foo {
     *     float var;
     *     float another;
     * }
     *
     * Foo fooU;
     *
     * fooU should be expanded to
     * - fooU.var
     * - fooU.another
     *
     * @param variable The uniform to expand
     * @param main The main struct (top-level) of the uniform
     * @param structs A list of GLSLStructs
     * @return List of expanded uniforms
     */
    public static List<String> expendVariable(Variable variable, GLSLStruct main, List<GLSLStruct> structs) {
        final List<String> r = new ArrayList<String>();

        // Cycle through all member variables
        for (Variable member : main.getMembers()) {
            LOGGER.trace("Working on member:{} {}", member.getType(), member.getName());

            // If it is primitive we can simply add it!
            if (isPrimitive(member)) {
                LOGGER.trace("Just added this member. Next!");
                r.add(member.getName());
            } else {

                // Otherwise we find the type (struct) in the list
                GLSLStruct s = findNameInList(member.getType(), structs);

                // If it is a known struct, added, otherwise the name of the variable ought to be enough
                if (s != null) {
                    LOGGER.trace("Making recursiv call to expand this member {} {} with struct provided: {}", member.getType(), member.getName(), s.getName());
                    // Recursive call to expand this struct
                    r.addAll(expendVariable(member, s, structs));
                } else {
                    LOGGER.trace("Missing struct for variable {} {}", variable.getType(), variable.getName());
                    // We could not find the struct => just add it.
                    r.add(member.getName());
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
        final List<String> finalList = new ArrayList<String>(r.size());
        for (String expanded : r) {
            // we need to prefix all expanded variables with the initial variable name!
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

        final List<Variable> primitives = new ArrayList<Variable>();

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

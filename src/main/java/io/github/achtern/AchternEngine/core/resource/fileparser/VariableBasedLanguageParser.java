package io.github.achtern.AchternEngine.core.resource.fileparser;

import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Variable;

import java.util.ArrayList;
import java.util.List;

public class VariableBasedLanguageParser {

    protected List<String> getNames(List<Variable> vars) {
        List<String> names = new ArrayList<String>();

        for (Variable v : vars) {
            names.add(v.getName());
        }

        return names;
    }

    protected List<String> getTypes(List<Variable> vars) {
        List<String> names = new ArrayList<String>();

        for (Variable v : vars) {
            names.add(v.getType());
        }

        return names;
    }
}

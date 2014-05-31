package io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses;

import java.util.ArrayList;
import java.util.List;

public class GLSLStruct {

    private String name;

    private List<Variable> members;

    public GLSLStruct(String name) {
        this(name, new ArrayList<Variable>());
    }

    public GLSLStruct(String name, List<Variable> members) {
        this.name = name;
        this.members = members;
    }

    public void addMember(Variable variable) {
        getMembers().add(variable);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Variable> getMembers() {
        return members;
    }

    public void setMembers(List<Variable> members) {
        this.members = members;
    }
}

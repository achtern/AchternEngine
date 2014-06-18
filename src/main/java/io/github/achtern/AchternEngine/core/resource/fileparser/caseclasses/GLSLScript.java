package io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses;

import java.util.ArrayList;
import java.util.List;

public class GLSLScript {

    public enum Type {
        VERTEX_SHADER,
        GEOMETRY_SHADER,
        FRAGMENT_SHADER
    }

    private String name;

    private Type type;

    private List<GLSLStruct> structs;

    private List<Variable> uniforms;

    private List<Variable> attributes;

    private List<String> expandedUniforms;

    private String source;


    public GLSLScript(String name, Type type) {
        this.name = name;
        this.type = type;
        this.structs = new ArrayList<GLSLStruct>();
        this.uniforms = new ArrayList<Variable>();
        this.attributes = new ArrayList<Variable>();
        this.expandedUniforms = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<GLSLStruct> getStructs() {
        return structs;
    }

    public void setStructs(List<GLSLStruct> structs) {
        this.structs = structs;
    }

    public List<Variable> getUniforms() {
        return uniforms;
    }

    public void setUniforms(List<Variable> uniforms) {
        this.uniforms = uniforms;
    }

    public List<Variable> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Variable> attributes) {
        this.attributes = attributes;
    }

    public List<String> getExpandedUniforms() {
        return expandedUniforms;
    }

    public void setExpandedUniforms(List<String> expandedUniforms) {
        this.expandedUniforms = expandedUniforms;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
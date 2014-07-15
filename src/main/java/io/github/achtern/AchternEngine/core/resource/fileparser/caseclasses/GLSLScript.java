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

    private List<Variable> attributes;

    private List<Uniform> uniforms;

    private String source;

    private boolean processed;


    public GLSLScript(String name, Type type) {
        this.name = name;
        this.type = type;
        this.structs = new ArrayList<GLSLStruct>();
        this.uniforms = new ArrayList<Uniform>();
        this.attributes = new ArrayList<Variable>();
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

    public List<Uniform> getUniforms() {
        return uniforms;
    }

    public void setUniforms(List<Uniform> uniforms) {
        this.uniforms = uniforms;
    }

    public List<Variable> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Variable> attributes) {
        this.attributes = attributes;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
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

package io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses;

import io.github.achtern.AchternEngine.core.bootstrap.NativeObject;

import java.util.ArrayList;
import java.util.List;

public class GLSLScript extends NativeObject {

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

    private List<Uniform> expandedUniforms;

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

    public void setUniformsFromVariable(List<Variable> uniforms) {
        List<Uniform> u = new ArrayList<Uniform>(uniforms.size());
        for (Variable v : uniforms) {
            u.add(new Uniform(v));
        }

        setUniforms(u);
    }

    public List<Uniform> getExpandedUniforms() {
        return expandedUniforms;
    }

    public void setExpandedUniforms(List<Uniform> expandedUniforms) {
        this.expandedUniforms = expandedUniforms;
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

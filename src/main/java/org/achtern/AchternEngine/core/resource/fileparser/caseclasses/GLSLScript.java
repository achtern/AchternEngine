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

package org.achtern.AchternEngine.core.resource.fileparser.caseclasses;

import org.achtern.AchternEngine.core.bootstrap.NativeObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class GLSLScript extends NativeObject {

    public enum Type {
        VERTEX_SHADER,
        GEOMETRY_SHADER,
        FRAGMENT_SHADER
    }

    protected String name;

    protected Type type;

    protected List<GLSLStruct> structs;

    protected List<Variable> attributes;

    protected List<Uniform> uniforms;

    protected List<Uniform> expandedUniforms;

    protected String source;

    protected boolean processed;


    public GLSLScript(String name, Type type) {
        this.name = name;
        this.type = type;
        this.structs = new ArrayList<GLSLStruct>();
        this.uniforms = new ArrayList<Uniform>();
        this.attributes = new ArrayList<Variable>();
    }

    public void setUniformsFromVariable(List<Variable> uniforms) {
        List<Uniform> u = new ArrayList<Uniform>(uniforms.size());
        for (Variable v : uniforms) {
            u.add(new Uniform(v));
        }

        setUniforms(u);
    }

}

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

package io.github.achtern.AchternEngine.core.resource.fileparser.nextgenshader.parser;

import io.github.achtern.AchternEngine.core.resource.fileparser.ParsingException;
import io.github.achtern.AchternEngine.core.resource.fileparser.nextgenshader.builder.manager.RequireManager;
import io.github.achtern.AchternEngine.core.resource.fileparser.nextgenshader.statement.RequireParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.nextgenshader.validator.MainBlockValidator;
import lombok.Getter;
import lombok.Setter;

public class VertexParser implements PipelineParser {

    @Setter @Getter protected String source;

    protected RequireManager requireManager;

    @Override
    public void parse() throws ParsingException {
        // Validate, if there is a valid main block!
        MainBlockValidator validator = new MainBlockValidator();

        if (!validator.isValid(getSource())) {
            throw new ParsingException("No valid 'void main () {/.../}' method found!");
        }

        // Get the require lines!

        final String[] lines = getSource().split("\n");

        this.requireManager = new RequireManager();
        final RequireParser requireParser = new RequireParser();
        for (String l : lines) {
            if (requireParser.test(l.trim())) {
                requireManager.add(requireParser.getType(l.trim()), requireParser.getName(l.trim()));
            }
        }

    }
}

/*
layout (location = 0) in vec3 inPosition;
layout (location = 1) in vec2 inTexCoord;
layout (location = 2) in vec3 inNormal;


@require mat4 model;
@require mat4 modelView;
@require mat4 MVP;
@require mat4 shadowMatrix;

void main ()
{
  gl_Position = MVP * vec4(inPosition, 1.0);

  @provide vec2 texCoord = inTexCoord;
  @provide vec3 normal = (model * vec4(inNormal, 0.0)).xyz;
  @provide vec3 worldPos = (model * vec4(inPosition, 1.0)).xyz;
  @provide vec4 shadowMapCoord = shadowMatrix * vec4(inPosition, 1.0);

  @yield;
}
 */

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

package io.github.achtern.AchternEngine.core.scenegraph.entity.controller;

import io.github.achtern.AchternEngine.core.GameDebugger;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.math.Quaternion;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Figure;
import io.github.achtern.AchternEngine.core.scenegraph.entity.QuickEntity;
import io.github.achtern.AchternEngine.core.scenegraph.scanning.FigureRetriever;

public class Chaser extends QuickEntity implements GameDebugger.DebugStateListener {

    public boolean debug;

    protected float speed;
    protected float minDist;
    protected float maxDist;

    protected Node chaseTo;

    protected boolean disabled;
    protected boolean idle;

    private Figure cacheFigure;

    private String tmpName;

    public Chaser(Node to, float speed) {
        this(to, speed, 5, 50);
    }

    public Chaser(String to, float speed) {
        this(to, speed, 5, 50);
    }

    public Chaser(Node to) {
        this(to, 10, 5, 50);
    }

    public Chaser(String to) {
        this(to, 10, 5, 50);
    }

    public Chaser(Node to, float speed, float minDist, float maxDist) {
        super("Chaser");
        this.chaseTo = to;
        this.speed = speed;
        this.minDist = minDist;
        this.maxDist = maxDist;
    }

    /**
     * Create an Chaser Entity
     */
    public Chaser(String to, float speed, float minDist, float maxDist) {
        super("Chaser");
        this.speed = speed;
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.tmpName = to;
    }

    /**
     * @see QuickEntity#update(float)
     */
    @Override
    public void update(float delta) {
        super.update(delta);

        if (chaseTo == null || tmpName == null) {
            init();
        }

        if (disabled) return;


        float distance = chaseTo.getTransform().getPosition().euclidean(getTransform().getPosition());


        if (!(distance > maxDist)) {
            look(delta);
        }

        if (distance > maxDist || distance < minDist) {
            if (!idle) {
                idle = true;
                if (debug) {
                    getFigure().getMaterial().setColor(Color.RED);
                }
            }

            return;
        }

        if (idle && debug) {
            getFigure().getMaterial().setColor(Color.GREEN);
        }

        idle = false;

        getTransform().getPosition().addLocal(getTransform().getRotation().getForward().mul(speed * delta));

    }


    protected void look(float delta) {
        Quaternion nR = getTransform().getFaceAt(chaseTo.getTransform().getTransformedPosition(), Transform.Y_AXIS);
        getTransform().setRotation(getTransform().getRotation().nlerp(nR, delta * 5.0f, true));
    }

    protected Figure getFigure() {
        return cacheFigure;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void disable() {
        setDisabled(true);
    }

    public void enable() {
        setDisabled(false);
    }

    public void toogle() {
        setDisabled(!isDisabled());
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        if (disabled && debug) {
            getFigure().getMaterial().setColor(Color.YELLOW);
        } else if (debug) {
            if (idle) {
                getFigure().getMaterial().setColor(Color.RED);
            } else {
                getFigure().getMaterial().setColor(Color.GREEN);
            }
        }
    }

    @Override
    public void changed(boolean enabled) {
        this.debug = enabled;
    }

    protected void init() {
        if (chaseTo == null) {
            chaseTo = getEngine().getGame().get(tmpName);
        } else {
            tmpName = chaseTo.getName();
        }

        FigureRetriever retriever = new FigureRetriever();
        retriever.scan(getParent());
        cacheFigure = retriever.get().get(0);
        if (getEngine().getGame().getDebugger() != null) {
            getEngine().getGame().getDebugger().register(this, true);
        }
    }
}

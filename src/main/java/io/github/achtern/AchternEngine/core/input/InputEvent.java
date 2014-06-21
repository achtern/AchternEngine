package io.github.achtern.AchternEngine.core.input;


public class InputEvent {

    protected final Key key;
    protected final float delta;

    public InputEvent(Key key, float delta) {
        this.key = key;
        this.delta = delta;
    }

    public Key getKey() {
        return key;
    }

    public float getDelta() {
        return delta;
    }
}

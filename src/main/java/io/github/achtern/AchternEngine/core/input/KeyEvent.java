package io.github.achtern.AchternEngine.core.input;


public class KeyEvent implements InputEvent {

    protected final Key key;
    protected final float delta;

    public KeyEvent(Key key, float delta) {
        this.key = key;
        this.delta = delta;
    }

    public Key getKey() {
        return key;
    }

    @Override
    public float getDelta() {
        return delta;
    }
}

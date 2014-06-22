package io.github.achtern.AchternEngine.core.input.event.payload;


import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;

public class KeyEvent implements InputEvent {

    protected final InputAdapter input;
    protected final Key key;
    protected final float delta;

    public KeyEvent(InputAdapter input, Key key, float delta) {
        this.input = input;
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

    @Override
    public InputAdapter getInputAdapter() {
        return input;
    }


}

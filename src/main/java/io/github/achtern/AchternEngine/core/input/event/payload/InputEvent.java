package io.github.achtern.AchternEngine.core.input.event.payload;

import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;

public interface InputEvent {


    public float getDelta();

    public InputAdapter getInputAdapter();

}

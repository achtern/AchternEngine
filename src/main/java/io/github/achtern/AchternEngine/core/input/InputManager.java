package io.github.achtern.AchternEngine.core.input;

import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.input.inputmap.KeyMap;
import io.github.achtern.AchternEngine.core.input.inputmap.MouseMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputManager.class);

    protected InputAdapter input;

    protected KeyMap keyMap;

    protected MouseMap mouseMap;

    public InputManager(InputAdapter input) {
        this.input = input;
    }

    public void trigger(float delta) {

        keyMap.trigger(delta);
        mouseMap.trigger(delta);
        input.update();
    }

    public KeyMap getKeyMap() {
        return keyMap;
    }

    public void setKeyMap(KeyMap keyMap) {
        this.keyMap = keyMap;
        this.keyMap.setInput(this.input);
    }

    public MouseMap getMouseMap() {
        return mouseMap;
    }

    public void setMouseMap(MouseMap mouseMap) {
        this.mouseMap = mouseMap;
        this.mouseMap.setInput(this.input);
    }
}

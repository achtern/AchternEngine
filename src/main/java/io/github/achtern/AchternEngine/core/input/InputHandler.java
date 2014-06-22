package io.github.achtern.AchternEngine.core.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InputHandler {

    protected InputAdapter input;

    protected List<InputMap> maps;

    public InputHandler(InputAdapter input, InputMap... maps) {
        this.input = input;
        this.maps = new ArrayList<InputMap>();

        Collections.addAll(this.maps, maps);
    }

    public void trigger(float delta) {

        for (InputMap m : maps) {
            m.trigger(delta);
        }
        input.update();
    }

}

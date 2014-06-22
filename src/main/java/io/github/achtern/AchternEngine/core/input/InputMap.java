package io.github.achtern.AchternEngine.core.input;

import java.util.List;
import java.util.Map;

public interface InputMap<T,L> {

    public InputMap register(T trigger, L h);

    public void trigger(float delta);


    public Map<T, List<L>> getListener();

    public void setListener(Map<T, List<L>> listener);

    public InputAdapter getInput();

    public void setInput(InputAdapter input);

}

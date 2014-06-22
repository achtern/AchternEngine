package io.github.achtern.AchternEngine.core.input.inputmap;

import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.input.event.listener.EventListener;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.Trigger;

import java.util.List;
import java.util.Map;

public interface InputMap<T extends Trigger,L extends EventListener> {

    public InputMap register(T trigger, L h);

    public void trigger(float delta);

    public Map<T, List<L>> getListener();

    public void setListener(Map<T, List<L>> listener);

    public InputAdapter getInput();

    public void setInput(InputAdapter input);

}

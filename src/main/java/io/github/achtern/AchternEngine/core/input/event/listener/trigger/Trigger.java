package io.github.achtern.AchternEngine.core.input.event.listener.trigger;

public interface Trigger<T> {

    public T get();

    public boolean accepts(T key);
}

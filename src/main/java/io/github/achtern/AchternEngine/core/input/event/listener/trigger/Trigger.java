package io.github.achtern.AchternEngine.core.input.event.listener.trigger;

public interface Trigger<T, E> {

    public T get();

    public E getType();
}

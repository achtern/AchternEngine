package io.github.achtern.AchternEngine.core.contracts;

import io.github.achtern.AchternEngine.core.rendering.binding.DataBinder;

/**
 * Indicates that the class can be bound as render target.
 */
public interface RenderTarget {

    /**
     * Binds the object as render target.
     * You should avoid to use this method.
     * @param binder The binder used to set the object
     */
    public void bindAsRenderTarget(DataBinder binder);

}

package io.github.achtern.AchternEngine.core.bootstrap;

/**
 * A NativeObject is used by the Graphics Engine
 * (e.g. OpenGL) and lives therefor most of the times
 * on the graphics card. It has an ID associated with it
 * and maybe buffers.
 * This is just the interface version!
 */
public interface Native {

    /**
     * Returns the corresponding ID,
     * -1 indicates, no ID has been set
     * @return ID
     */
    public int getID();

    /**
     * internal use only
     */
    public void setID(int id);

}

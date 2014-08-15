package io.github.achtern.AchternEngine.core.bootstrap;

/**
 * A NativeObject is used by the Graphics Engine
 * (e.g. OpenGL) and lives therefor most of the times
 * on the graphics card. It has an ID associated with it
 * and maybe buffers.
 */
public abstract class NativeObject implements Native {

    /**
     * The ID is used to identify the object.
     * -1 indicates, that the ID has not been
     * set yet and has to be uploaded to the
     * Graphics Engine!
     */
    private int id = -1;

    public NativeObject() {
    }


    /**
     * Returns the corresponding ID,
     * -1 indicates, no ID has been set
     * @return ID
     */
    @Override
    public int getID() {
        return id;
    }

    /**
     * internal use only
     */
    @Override
    public void setID(int id) {
        this.id = id;
    }
}

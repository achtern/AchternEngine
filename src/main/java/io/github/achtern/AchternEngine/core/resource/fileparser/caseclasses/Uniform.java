package io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses;

import io.github.achtern.AchternEngine.core.rendering.binding.UniformManager;

public class Uniform extends Variable {

    protected int location = -1;

    protected Object value;

    protected boolean shouldSet = true;

    protected SetStrategy setStrategy;


    public Uniform(Variable from) {
        this(from.getType(), from.getName());
    }

    public Uniform(String type, String name) {
        super(type, name);
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SetStrategy getSetStrategy() {
        return setStrategy;
    }

    public void setSetStrategy(SetStrategy setStrategy) {
        this.setStrategy = setStrategy;
    }

    public boolean shouldSet() {
        return shouldSet;
    }

    public void setShouldSet(boolean shouldSet) {
        this.shouldSet = shouldSet;
    }

    @Override
    public String toString() {
        return "[" + getType() + " " + getName() + "]";
    }

    public static interface SetStrategy {

        public void set(Uniform uniform, UniformManager uniformManager);

    }
}

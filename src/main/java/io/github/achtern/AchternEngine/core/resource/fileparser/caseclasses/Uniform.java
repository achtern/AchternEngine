package io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses;

public class Uniform extends Variable {

    protected int location = -1;

    protected Object value;
    protected Class valueType;

    protected boolean shouldSet = true;

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

    public <T> void setValue(Class<T> type, T value) {
        this.valueType = type;
        this.value = value;
    }

    public Class getValueType() {
        return valueType;
    }

    public boolean shouldSet() {
        return shouldSet;
    }

    public void setShouldSet(boolean isTexture) {
        this.shouldSet = isTexture;
    }

    @Override
    public String toString() {
        return "[" + getType() + " " + getName() + "]";
    }
}

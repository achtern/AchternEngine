package io.github.achtern.AchternEngine.core.resource.fileparser.mesh;

public class OBJIndex {

    private int vertex;
    private int texCoord;
    private int normal;


    public boolean equals(OBJIndex index) {

        return (
                index.getVertex() == getVertex() &&
                index.getTexCoord() == getTexCoord() &&
                index.getNormal() == getNormal()
        );

    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof OBJIndex && equals((OBJIndex) obj));
    }

    @Override
    public int hashCode() {
        /**
         * Taken from http://stackoverflow.com/a/3934220/1933324
         */
        int hash = 17;
        hash = ((hash + getVertex()) << 5) - (hash + getVertex());
        hash = ((hash + getTexCoord()) << 5) - (hash + getTexCoord());
        hash = ((hash + getNormal()) << 5) - (hash + getNormal());
        return hash;
    }

    public int getVertex() {
        return vertex;
    }

    public void setVertex(int vertex) {
        this.vertex = vertex;
    }

    public int getTexCoord() {
        return texCoord;
    }

    public void setTexCoord(int texCoord) {
        this.texCoord = texCoord;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }
}

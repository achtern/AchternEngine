package io.github.achtern.AchternEngine.core.resource.fileparser.mesh;

import java.io.BufferedReader;
import java.io.IOException;

public interface Model {

    public void parse(BufferedReader meshReader) throws IOException;

    public IndexedModel toIndexedModel();

}

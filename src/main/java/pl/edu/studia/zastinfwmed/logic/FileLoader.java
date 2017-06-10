package pl.edu.studia.zastinfwmed.logic;

import java.io.File;

/**
 * Created by Alicja on 2017-05-13.
 */
public class FileLoader {

    public File loadFileFromResources(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }
}

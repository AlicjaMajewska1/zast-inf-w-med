package pl.edu.studia.zastinfwmed.logic;

import pl.edu.studia.zastinfwmed.web.DataFile;

import java.io.File;

/**
 * Created by Alicja on 2017-05-13.
 */
public class FileLoader {

    public File loadFileFromResources(DataFile dataFile) {
        if (dataFile.isResources()) {
            ClassLoader classLoader = getClass().getClassLoader();
            return new File(classLoader.getResource(dataFile.getName()).getFile());
        } else {
            return new File(dataFile.getAbsolutePath());
        }
    }
}

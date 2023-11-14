package main.java.photos.utils;

import java.io.File;
import java.io.IOException;

public class Tools {
    public static File getDataDir() throws IOException {
        String projectRootDirectory = System.getProperty("user.dir");

        String dataDirectory = projectRootDirectory + File.separator + "data";

        File dataDirFileObj = new File(dataDirectory);
        if (!dataDirFileObj.isDirectory()) {
            throw new IOException("Main data directory does not exist!");
        }

        return dataDirFileObj;
    }
}

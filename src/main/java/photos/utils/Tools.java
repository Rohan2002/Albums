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

    public static boolean deleteDirectory(File directory) {
        if (!directory.isDirectory()) {
            return false;
        }
        // Get all files and subdirectories in the directory
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                // Recursive delete for subdirectories
                deleteDirectory(file);
            }
        }

        // Finally, delete the empty directory
        return directory.delete();
    }
}

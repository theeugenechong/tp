package cooper.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Storage {

    protected final Path filePath;

    public Storage(String filePath) {
        this.filePath = Path.of(filePath);
    }

    protected static Scanner getScanner(Path filePath) {
        File storageFile = new File(filePath.toString());
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(storageFile);
        } catch (FileNotFoundException e) {
            try {
                createFileInDirectory(filePath.toString());
            } catch (IOException ioException) {
                System.out.println("Error creating storage file: " + ioException.getMessage());
            }
        }
        return fileScanner;
    }

    /**
     * Creates a file with the path specified by {@code filePath}.
     *
     * @param filePath string representing the file path
     * @throws IOException if there is an error creating the file
     */
    private static void createFileInDirectory(String filePath) throws IOException {
        String directoryName = getDirectoryPath(filePath);
        Files.createDirectories(Paths.get(directoryName));
        Files.createFile(Paths.get(filePath));
    }

    /**
     * Helper function which returns the full directory path of {@code filePath}. The purpose of this method
     * is so that the directory for the storage file can be created first before the file is created in the
     * {@code createFileInDirectory} method.
     *
     * @param filePath string representing the file path
     * @return a string representing the full directory path of {@code filePath}
     */
    private static String getDirectoryPath(String filePath) {
        String[] directoryPathAsArray = filePath.split("/");
        StringBuilder directoryPath = new StringBuilder();

        /* Iterate up to length - 1 because the last argument in a file path is usually the file type */
        for (int i = 0; i < (directoryPathAsArray.length - 1); i++) {
            directoryPath.append(directoryPathAsArray[i]).append("/");
        }
        return String.valueOf(directoryPath);
    }
}

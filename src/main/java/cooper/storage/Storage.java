package cooper.storage;

import cooper.ui.FileIoUi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//@@author theeugenechong

public class Storage {

    protected final String filePath;

    protected static final String SLASH = "/";
    protected static final String SEPARATOR = " | ";
    protected static final String SEPARATOR_REGEX = "\\|";

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Gets a scanner object to read from a file. If the file is not found, the file is created correctly.
     * @param filePath Path of the file
     * @return Scanner to read that file
     */
    protected Scanner getScanner(String filePath) {
        File storageFile = new File(filePath);
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(storageFile);
        } catch (FileNotFoundException e) {
            try {
                createFileInDirectory(filePath);
            } catch (IOException ioe) {
                FileIoUi.showFileCreationError(ioe);
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
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createFileInDirectory(String filePath) throws IOException {
        String directoryName = getDirectoryPath(filePath);
        File storageDir = new File(directoryName);
        storageDir.mkdir();

        File storageFile = new File(filePath);
        storageFile.createNewFile();
    }

    /**
     * Helper function which returns the full directory path of {@code filePath}. The purpose of this method
     * is so that the directory for the storage file can be created first before the file is created in the
     * {@code createFileInDirectory} method.
     *
     * @param filePath string representing the file path
     * @return a string representing the full directory path of {@code filePath}
     */
    private String getDirectoryPath(String filePath) {
        String[] directoryPathAsArray = filePath.split(SLASH);
        StringBuilder directoryPath = new StringBuilder();

        /* Iterate up to length - 1 because the last argument in a file path is usually the file type */
        for (int i = 0; i < (directoryPathAsArray.length - 1); i++) {
            directoryPath.append(directoryPathAsArray[i]);
        }
        return String.valueOf(directoryPath);
    }
}

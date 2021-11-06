package cooper.ui;

import cooper.exceptions.InvalidFileDataException;

import java.io.IOException;

//@@author theeugenechong

/**
 * Contains the constants and methods for the Ui involving cOOPer's file handling.
 */
public class FileIoUi extends Ui {

    private static final String FILE_WRITE_ERROR = "Error writing to file: ";
    private static final String STORAGE_FILE_CREATION_ERROR = "Error creating storage file: ";
    private static final String INVALID_FILE_DATA = "Invalid file data in storage file: ";

    private static final String PDF_SUCCESSFUL = "The PDF has been successfully generated!";
    private static final String PDF_GENERATION_ERROR = "There was an error generating the PDF: ";

    private static final String CREATING_BACKUP = "Creating backup file now...";
    private static final String BACKUP_FILE_SUCCESSFUL = "The backup .txt file has been successfully created!";
    private static final String BACKUP_CREATION_ERROR = "There was an error creating the backup .txt file: ";

    private static final String POST_REQUEST_ERROR = "Error encountered when sending post request!";
    private static final String CONNECTION_PROBLEM = "There was a problem with your connection.";

    private static final String LATEX_URL_INCORRECT = "The URL provided for LaTex compilation is incorrect!";


    /**
     * Informs user that there was an error writing data to the file.
     **/
    public static void showFileWriteError(IOException e) {
        show(LINE);
        show(FILE_WRITE_ERROR, false);
        show(e.getMessage(), true);
        show(LINE);
    }

    /**
     * Informs user that there was an error in creating the file.
     **/
    public static void showFileCreationError(IOException e) {
        show(LINE);
        show(STORAGE_FILE_CREATION_ERROR, false);
        show(e.getMessage(), true);
        show(LINE);
    }

    /**
     * Informs user that the data in the storage file is of the wrong format.
     */
    public static void showInvalidFileDataError(InvalidFileDataException e) {
        show(LINE);
        show(INVALID_FILE_DATA, false);
        show(e.getMessage(), true);
        show(LINE);
    }

    /**
     * Informs user that the pdf file has been created successfully using {@code generate}.
     */
    public static void showPdfSuccessfullyGenerated() {
        show(LINE);
        show(PDF_SUCCESSFUL);
        show(LINE);
    }

    /**
     * Informs user that there was an error creating the pdf along with the error.
     */
    public static void showPdfGenerationError(IOException e) {
        show(LINE);
        show(PDF_GENERATION_ERROR, false);
        show(e.getMessage(), true);
        show(LINE);
    }

    /**
     * Informs use that the backup file for the pdf has been created successfully.
     */
    public static void showBackupFileSuccessfullyCreated() {
        show(LINE);
        show(BACKUP_FILE_SUCCESSFUL);
        show(LINE);
    }

    /**
     * Informs the user that there was an error creating the backup file for the pdf.
     */
    public static void showBackupFileCreationError(IOException e) {
        show(LINE);
        show(BACKUP_CREATION_ERROR, false);
        show(e.getMessage(), true);
        show(LINE);
    }

    /**
     * Informs user there was an error making the JSON post request when creating the PDF file.
     */
    public static void showPostRequestError() {
        show(LINE);
        show(POST_REQUEST_ERROR);
        show(LINE);
    }

    /**
     * Informs the user that there was a connection error when creating the PDF file.
     */
    public static void showConnectionError() {
        show(LINE);
        show(CONNECTION_PROBLEM);
        show(CREATING_BACKUP);
        show(LINE);
    }

    /**
     * Informs the user that the URL for creating the PDF file is invalid.
     */
    public static void showMalformedUrlError() {
        show(LINE);
        show(LATEX_URL_INCORRECT);
        show(LINE);
    }
}

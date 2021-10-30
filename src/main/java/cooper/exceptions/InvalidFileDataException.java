package cooper.exceptions;

import java.io.IOException;

//@@author theeugenechong

/**
 * Exception to signal that data in cOOPer's storage files are not of the correct format, hence cannot be loaded
 * to cOOPer upon entry to the program.
 */
public class InvalidFileDataException extends IOException {

    public InvalidFileDataException(String fileName) {
        super(fileName);
    }
}

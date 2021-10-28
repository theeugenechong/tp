package cooper.exceptions;

import java.io.IOException;

//@@author theeugenechong

public class InvalidFileDataException extends IOException {

    public InvalidFileDataException(String fileName) {
        super(fileName);
    }
}

package cooper.exceptions;

import java.io.IOException;

public class InvalidFileDataException extends IOException {

    public InvalidFileDataException(String fileName) {
        super(fileName);
    }
}

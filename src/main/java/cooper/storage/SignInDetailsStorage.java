package cooper.storage;

import cooper.exceptions.InvalidFileDataException;
import cooper.ui.FileIoUi;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.verification.Verifier;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//@@author theeugenechong

public class SignInDetailsStorage extends Storage {

    protected static final String ADMIN = "A";
    protected static final String EMPLOYEE = "E";
    protected static final String SIGN_IN_DETAILS_TXT = "signInDetails.txt";

    public SignInDetailsStorage(String filePath) {
        super(filePath);
    }

    public void loadSignInDetails(Verifier cooperVerifier) {
        HashMap<String, SignInDetails> registeredUsers = cooperVerifier.getRegisteredUsers();
        Scanner fileScanner = getScanner(filePath);
        readSignInDetails(fileScanner, registeredUsers);
    }

    public void saveSignInDetails(Verifier cooperVerifier) {
        try {
            writeSignInDetails(filePath, cooperVerifier.getRegisteredUsers());
        } catch (IOException e) {
            FileIoUi.showFileWriteError(e);
            System.exit(1);
        }
    }

    private static void readSignInDetails(Scanner fileScanner, HashMap<String, SignInDetails> registeredUsers) {
        if (fileScanner == null) {
            return;
        }

        while (fileScanner.hasNext()) {
            String signInDetails = fileScanner.nextLine();
            try {
                SignInDetails decodedSignInDetails = decodeSignInDetails(signInDetails);
                registeredUsers.put(decodedSignInDetails.getUsername(), decodedSignInDetails);
            } catch (InvalidFileDataException e) {
                FileIoUi.showInvalidFileDataError(e);
            }
        }
    }

    private static SignInDetails decodeSignInDetails(String signInDetailsAsString) throws InvalidFileDataException {
        String[] signInDetails = signInDetailsAsString.split(SEPARATOR_REGEX);
        if (isInvalidFileData(signInDetails)) {
            throw new InvalidFileDataException(SIGN_IN_DETAILS_TXT);
        }
        assert !isInvalidFileData(signInDetails);

        String username = signInDetails[0].trim();
        String userEncryptedPassword = signInDetails[1].trim();
        String userSalt = signInDetails[2].trim();
        UserRole userRole = signInDetails[3].trim().equals(ADMIN) ? UserRole.ADMIN : UserRole.EMPLOYEE;

        return new SignInDetails(username, userEncryptedPassword, userSalt, userRole);
    }

    private static boolean isInvalidFileData(String[] signInDetails) {
        if (signInDetails.length != 4) {
            return true;
        }

        if (!signInDetails[3].trim().equals(ADMIN) && !signInDetails[3].trim().equals(EMPLOYEE)) {
            return true;
        }

        for (String s : signInDetails) {
            if (s.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static void writeSignInDetails(String filePath, HashMap<String, SignInDetails> registeredUsers)
            throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, false);

        for (Map.Entry<String, SignInDetails> e : registeredUsers.entrySet()) {
            String encodedSignInDetails = encodeSignInDetails(e);
            fileWriter.write(encodedSignInDetails + System.lineSeparator());
        }
        fileWriter.close();
    }

    private static String encodeSignInDetails(Map.Entry<String, SignInDetails> registeredUser) {
        StringBuilder encodedSignInDetails = new StringBuilder();

        String username = registeredUser.getKey();
        encodedSignInDetails.append(username).append(SEPARATOR);

        String userEncryptedPassword = registeredUser.getValue().getUserEncryptedPassword();
        encodedSignInDetails.append(userEncryptedPassword).append(SEPARATOR);

        String userSalt = registeredUser.getValue().getUserSalt();
        encodedSignInDetails.append(userSalt).append(SEPARATOR);

        String userRole = registeredUser.getValue().getUserRole().equals(UserRole.ADMIN) ? ADMIN : EMPLOYEE;
        encodedSignInDetails.append(userRole);

        return String.valueOf(encodedSignInDetails);
    }
}


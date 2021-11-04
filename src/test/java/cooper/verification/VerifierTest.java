package cooper.verification;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@@author theeugenechong

public class VerifierTest {

    static Verifier verifier;

    @BeforeAll
    static void setUpVerifier() {
        verifier = new Verifier();
    }

    @Test
    @Order(1)
    void verify_properInputFirstLoginAttempt_loginFailed() {
        String input = "login Topias /pw 1111 /as admin";
        SignInDetails actual = verifier.verify(input);
        assertFalse(verifier.isSuccessfullySignedIn());
    }

    @Test
    @Order(2)
    void verify_properInputRegisterUser_registrationSuccessful() {
        String input = "register Martin /pw 1111 /as admin";
        SignInDetails actual = verifier.verify(input);

        String userSalt = verifier.getRegisteredUsers().get("Martin").getUserSalt();
        String userEncryptedPassword = PasswordHasher.generatePasswordHash("1111", userSalt);
        assertEquals(verifier.getRegisteredUsers().get("Martin").getUserEncryptedPassword(), userEncryptedPassword);
        assertFalse(verifier.isSuccessfullySignedIn());
        assertTrue(verifier.getRegisteredUsers().containsKey("Martin"));
    }

    @Test
    @Order(3)
    void verify_properInputLoginAfterRegister_loginSuccessful() {
        String input = "register Martin /pw 1111 /as admin";
        SignInDetails actual = verifier.verify(input);

        String userSalt = verifier.getRegisteredUsers().get("Martin").getUserSalt();
        String userEncryptedPassword = PasswordHasher.generatePasswordHash("1111", userSalt);
        SignInDetails expected = new SignInDetails("Martin", userEncryptedPassword, userSalt, UserRole.ADMIN);
        assertTrue(hasSameAttributeValuesAs(actual, expected));

        input = "login Martin /pw 1111 /as admin";
        actual = verifier.verify(input);

        assertTrue(verifier.isSuccessfullySignedIn());
    }

    @Test
    @Order(4)
    void verify_wrongPasswordLoginAfterRegister_loginUnsuccessful() {
        String input = "login Martin /pw 1234 /as admin";
        SignInDetails actual = verifier.verify(input);

        String userSalt = verifier.getRegisteredUsers().get("Martin").getUserSalt();
        String userEncryptedPassword = PasswordHasher.generatePasswordHash("1111", userSalt);
        SignInDetails expected = new SignInDetails("Martin", userEncryptedPassword, userSalt, UserRole.ADMIN);
        assertFalse(hasSameAttributeValuesAs(actual, expected));

        assertFalse(verifier.isSuccessfullySignedIn());
        assertTrue(verifier.getRegisteredUsers().containsKey("Martin"));
    }

    @Test
    @Order(5)
    void verify_wrongRoleLoginAfterRegister_loginUnsuccessful() {
        String input = "login Martin /pw 1111 /as employee";
        SignInDetails actual = verifier.verify(input);

        String userSalt = verifier.getRegisteredUsers().get("Martin").getUserSalt();
        String userEncryptedPassword = PasswordHasher.generatePasswordHash("1111", userSalt);
        SignInDetails expected = new SignInDetails("Martin", userEncryptedPassword, userSalt, UserRole.ADMIN);
        assertFalse(hasSameAttributeValuesAs(actual, expected));

        assertFalse(verifier.isSuccessfullySignedIn());
    }

    @Test
    @Order(6)
    void verify_improperInputEmptyUsernameField_failure() {
        String input = "login as admin";
        SignInDetails signInDetails = verifier.verify(input);

        assertNull(signInDetails);
        assertFalse(verifier.isSuccessfullySignedIn());
    }

    @Test
    @Order(7)
    void verify_improperInputEmptyRoleField_failure() {
        String input = "login Sebastian as";
        SignInDetails signInDetails = verifier.verify(input);

        assertNull(signInDetails);
        assertFalse(verifier.isSuccessfullySignedIn());
    }

    @Test
    @Order(8)
    void verify_emptyInputString_failure() {
        String input = "";
        SignInDetails signInDetails = verifier.verify(input);

        assertNull(signInDetails);
        assertFalse(verifier.isSuccessfullySignedIn());
    }

    @Test
    @Order(9)
    void verify_emptyArguments_failure() {
        String input = "register";
        SignInDetails signInDetails = verifier.verify(input);

        assertNull(signInDetails);
        assertFalse(verifier.isSuccessfullySignedIn());
    }

    private static boolean hasSameAttributeValuesAs(SignInDetails actual, SignInDetails expected) {
        boolean hasSameUsername = actual.getUsername().equals(expected.getUsername());
        boolean hasSameRole = actual.getUserRole().equals(expected.getUserRole());
        boolean hasSamePassword = actual.getUserEncryptedPassword().equals(expected.getUserEncryptedPassword());
        return (hasSameUsername && hasSameRole && hasSamePassword);
    }
}

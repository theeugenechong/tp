package cooper.verification;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * This class generates a hash for user passwords using the PBKDF2 hash algorithm.
 * To ensure higher security, a salt text is added to the password before hashing it.
 * The implementation of the password hashing is adapted from this website:
 * https://www.quickprogrammingtips.com/java/how-to-securely-store-passwords-in-java.html
 */
public class PasswordHasher {

    /* SHA-1 produces a 160-bit hash value */
    private static final int KEY_LENGTH = 160;

    /* National Institute of Standards and Technology (NIST) recommends at least 10000 iterations of the hash
     * function be performed.
     **/
    private static final int ITERATIONS = 20000;

    /**
     * Generates hash for {@code password} with {@code salt} added to it.
     *
     * @param password Password entered by the user
     * @param salt Salt text used to increase security of hashing
     * @return Password hash
     */
    public static String generatePasswordHash(String password, String salt) {
        byte[] encodedBytes = null;

        try {
            String algorithm = "PBKDF2WithHmacSHA1";

            byte[] saltBytes = Base64.getDecoder().decode(salt);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);

            encodedBytes = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        assert encodedBytes != null;
        return Base64.getEncoder().encodeToString(encodedBytes);
    }

    /**
     * Generates 64-bit salt text which is to be added to the password for hashing.
     *
     * @return Salt text which has been generated
     */
    public static String getNewSalt() {
        byte[] salt = new byte[8];

        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.nextBytes(salt);
        } catch (NoSuchAlgorithmException nse) {
            nse.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(salt);
    }
}

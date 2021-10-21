package cooper.verification;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordHasher {

    private static final int KEY_LENGTH = 160;
    private static final int ITERATIONS = 20000;

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

    public static String getNewSalt() {
        byte[] salt = new byte[8];

        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(salt);
    }
}

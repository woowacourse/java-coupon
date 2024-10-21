package coupon.member.domain;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class SaltGenerator {

    private static final int SALT_BYTE_LENGTH = 16;

    public String generate() {
        byte[] salt = new byte[SALT_BYTE_LENGTH];
        try {
            SecureRandom sr = SecureRandom.getInstanceStrong();
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(salt);
    }
}

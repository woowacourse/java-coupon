package coupon.member.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class HashedPasswordCreator {

    private static final String HASH_ALGORITHM_TYPE = "SHA-256";

    public Password createPassword(String plainPassword, String salt) {
        return new Password(hashPassword(plainPassword, salt));
    }

    private String hashPassword(String password, String salt) {
        try {
            String saltedPassword = password + salt;
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM_TYPE);
            byte[] hashedPassword = md.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

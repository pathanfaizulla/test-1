package com.insignia.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtility {

    @Value("${encryption.strength:12}")
    private int strengthFactor = 12;

    private final BCryptPasswordEncoder encoder =
			new BCryptPasswordEncoder(strengthFactor);

    public boolean matchBCryptDecryptedPassword(String plainTextPassword,
												String hashedPassword) {
        return encoder.matches(plainTextPassword, hashedPassword);
    }

    public String getBCryptEncryptedPassword(String password) {
        return encoder.encode(password);
    }
}

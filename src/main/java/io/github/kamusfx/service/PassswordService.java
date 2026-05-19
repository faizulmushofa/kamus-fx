package io.github.kamusfx.service;

import org.mindrot.jbcrypt.BCrypt;

import io.github.kamusfx.MiniContainer.Auto;

@Auto
public class PassswordService {

    public String hash(String password) {
        return BCrypt.hashpw(
                password,
                BCrypt.gensalt()
        );
    }

    public boolean verify(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(
                rawPassword,
                hashedPassword
        );
    }
}

package io.github.kamusfx.service.implementation;

import io.github.kamusfx.MiniContainer.Auto;
import io.github.kamusfx.model.User;
import io.github.kamusfx.service.PassswordService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

@Auto
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceImp userService;
    private final PassswordService passswordService;


    public boolean login(String username, String password) throws SQLException {

        if (username == null || password == null) {
            return false;
        }
        User user = userService.getUserByUsername(username);

        if (user == null) {
            return false;
        }

        if (passswordService.verify(password, user.getPassword())) {
            return true;
        }

        return false;
    }

    public boolean register(String username, String password, String confirmPassword) throws SQLException {

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        User newUser = User.builder()
                        .username(username)
                                .password(password)
                                        .build();

       if (userService.createUser(newUser).isEmpty()){
           return false;
       }
       return true;
    }

}

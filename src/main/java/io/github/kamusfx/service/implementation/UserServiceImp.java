package io.github.kamusfx.service.implementation;

import io.github.kamusfx.MiniContainer.Auto;
import io.github.kamusfx.model.User;
import io.github.kamusfx.repository.User.UserRepository;
import io.github.kamusfx.service.PassswordService;
import io.github.kamusfx.service.UserService;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Auto
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PassswordService passswordService;

    @Override
    public Optional<User> createUser(User user) throws SQLException {

        String hashPassword = passswordService.hash(user.getPassword());

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(hashPassword);

        userRepository.insert(newUser);
        return Optional.of(newUser) ;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        userRepository.update(user);
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        User user = userRepository.findById(id);
        userRepository.delete(user);
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userRepository.findAll();
    }
}

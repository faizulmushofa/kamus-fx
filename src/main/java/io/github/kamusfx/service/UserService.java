package io.github.kamusfx.service;

import io.github.kamusfx.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> createUser(User user) throws SQLException;

    void updateUser(User user) throws SQLException;

    void deleteUser(int id) throws SQLException;

    User getUserById(int id);

    User getUserByUsername(String username) throws SQLException;

    List<User> getAllUsers() throws SQLException;
}
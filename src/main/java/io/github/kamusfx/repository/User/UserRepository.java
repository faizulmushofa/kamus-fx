package io.github.kamusfx.repository.User;

import io.github.kamusfx.model.User;
import io.github.kamusfx.repository.SqliteDatabaseImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import io.github.kamusfx.MiniContainer.Auto;

@Auto
public class UserRepository {
    private final Connection connection;

    public UserRepository() throws SQLException {
        this.connection = SqliteDatabaseImp.getConnection();
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(UserQuery.FIND_ALL)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(mapResultSet(resultSet));
            }


        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }

        return users;
    }

    public User findByUsername(String username) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(UserQuery.FIND_BY_USERNAME)){
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSet(resultSet);
            }
        }
        return null;
    }

    public User findById(int id) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement(UserQuery.FIND_BY_ID)){

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSet(resultSet);
            }
        }

        return null;
    }

    public void insert(User user) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(UserQuery.INSERT)){
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            statement.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public void update(User user) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(UserQuery.UPDATE)){
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, String.valueOf(user.getId()));

            statement.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }

    }

    public void delete(User user) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(UserQuery.DELETE)){
            statement.setString(1, String.valueOf(user.getId()));

            statement.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    private static final DateTimeFormatter SQLITE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private User mapResultSet(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        
        String createdAtStr = rs.getString("createdAt");
        if (createdAtStr != null) {
            if (createdAtStr.contains("T")) {
                user.setCreatedAt(LocalDateTime.parse(createdAtStr));
            } else {
                user.setCreatedAt(LocalDateTime.parse(createdAtStr, SQLITE_DATE_FORMATTER));
            }
        }

        String updatedAtStr = rs.getString("updatedAt");
        if (updatedAtStr != null) {
            if (updatedAtStr.contains("T")) {
                user.setUpdatedAt(LocalDateTime.parse(updatedAtStr));
            } else {
                user.setUpdatedAt(LocalDateTime.parse(updatedAtStr, SQLITE_DATE_FORMATTER));
            }
        }

        return user;
    }
}

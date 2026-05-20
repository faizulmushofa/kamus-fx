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


/**
 * Repository untuk akses data user ke database
 *
 * bertanggung jawab menangani operasi penyimpanan
 * dan pengambilan data word dari database Sqlite,termasuk
 * pencarian dan penyimpanan
 *
 * @author its_me20
 * @version 1.0
 * @since 2026-05-20
 */
@Auto
public class UserRepository {
    private final Connection connection;

    public UserRepository() throws SQLException {
        this.connection = SqliteDatabaseImp.getConnection();
    }

    /**
     * Mengembalikan semua nilai user dari database
     *
     * @return list dari semua entity user
     * @throws SQLException apabila database Error
     */
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
    /**
     * Mencari di database dengan kata kunci username
     *
     * @param username
     *
     * @return Obj user jika di temukan dan null jika tidak
     * @throws SQLException apabila terjadi kesalahan pada database
     */
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

    /**
     * Mencari user berdasarkan ID uniknya di database.
     *
     * @param id ID user yang akan dicari
     * @return objek User jika ditemukan, atau null jika tidak ada data
     * @throws SQLException apabila terjadi kesalahan pada database
     */
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

    /**
     * Menyimpan data user baru ke dalam database.
     *
     * @param user objek data User yang akan disimpan
     * @throws SQLException apabila terjadi kesalahan pada database
     */
    public void insert(User user) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(UserQuery.INSERT)){
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            statement.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * Memperbarui data user yang sudah ada di database.
     *
     * @param user objek User berisi data baru untuk diperbarui
     * @throws SQLException apabila terjadi kesalahan pada database
     */
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

    /**
     * Menghapus data user dari database berdasarkan ID.
     *
     * @param user objek User yang akan dihapus dari database
     * @throws SQLException apabila terjadi kesalahan pada database
     */
    public void delete(User user) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(UserQuery.DELETE)){
            statement.setString(1, String.valueOf(user.getId()));

            statement.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    private static final DateTimeFormatter SQLITE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Mengubah data ResultSet menjadi objek User.
     *
     * @param rs hasil kueri dari database
     * @return objek User yang telah dipetakan
     * @throws SQLException apabila terjadi kesalahan saat membaca ResultSet
     */
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

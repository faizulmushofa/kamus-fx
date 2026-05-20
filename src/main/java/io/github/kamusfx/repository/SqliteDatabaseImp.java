package io.github.kamusfx.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Kelas implementasi koneksi database SQLite
 *
 * bertanggung jawab menangani inisialisasi, penyediaan,
 * dan penutupan koneksi database SQLite.
 *
 * @author its_me20
 * @version 1.0
 * @since 2026-05-20
 */
public class SqliteDatabaseImp{

    private static final String URL = "jdbc:sqlite:src/main/resources/Data/dictionary";
    private static Connection connection;

    /**
     * Mendapatkan atau membuat koneksi database SQLite yang bersifat singleton.
     *
     * @return objek Connection ke database SQLite
     * @throws SQLException jika terjadi kegagalan saat membuat koneksi
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(URL);
        }
        return connection;
    }


    /**
     * Menutup koneksi database jika koneksi sedang terbuka.
     *
     * @throws SQLException jika terjadi kegagalan saat menutup koneksi
     */
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}

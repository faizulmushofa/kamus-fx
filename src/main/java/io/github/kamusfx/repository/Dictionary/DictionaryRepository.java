package io.github.kamusfx.repository.Dictionary;

import io.github.kamusfx.model.Word;
import io.github.kamusfx.repository.SqliteDatabaseImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import io.github.kamusfx.MiniContainer.Auto;

/**
 * Repository untuk akses data kamus ke database
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
public class DictionaryRepository {

    private final Connection connection;


    public DictionaryRepository() throws SQLException {
        this.connection = SqliteDatabaseImp.getConnection();
    }

    /**
     * Mengembalikan semua nilai words dari database
     *
     * @return list dari semua entity word
     * @throws SQLException apabila database Error
     */
    public List<Word> findAll() throws SQLException {
        List<Word> words = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(DictionaryQuery.FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Word word = mapToWord(resultSet);

                words.add(word);
            }
        }catch(SQLException e){
            throw new SQLException("Failed to fetch words", e);
        }

        return words;
    }

    /**
     * Menyimpan data Word baru ke dalam database.
     *
     * @param word data Word yang akan disimpan
     * @throws SQLException jika terjadi kesalahan saat proses insert ke database
     */
    public void insert(Word word) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(DictionaryQuery.INSERT)) {
            statement.setString(1, word.getIndonesia());
            statement.setString(2, word.getEnglish());

            statement.executeUpdate();



        }catch(SQLException e){
            throw new SQLException("Failed to insert word", e);
        }

    }

    /**
     * Mencari data Word berdasarkan kata dalam bahasa Indonesia.
     *
     * @param indonesia kata bahasa Indonesia yang akan dicari
     * @return objek Word jika ditemukan, atau null jika tidak ada data
     * @throws SQLException jika terjadi kesalahan saat akses database
     */
    public Word findByIndonesia(String indonesia) throws SQLException {

        try(PreparedStatement statement = connection.prepareStatement(DictionaryQuery.FIND_BY_INDONESIA)) {
            statement.setString(1, indonesia);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Word word = mapToWord(resultSet);
                return word;
            }
        }catch(SQLException e){
            throw new SQLException("Failed to fetch word", e);
        }
        return null;
    }

    /**
     * Mencari data Word berdasarkan kata dalam bahasa Inggris
     *
     * @param english kata bahasa Inggris yang akan dicari
     * @return objek Word jika ditemukan, atau null jika tidak ada data
     * @throws SQLException jika terjadi kesalahan saat akses database
     */
    public Word findByEnglish(String english) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(DictionaryQuery.FIND_BY_ENGLISH)) {
            statement.setString(1, english);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Word word = mapToWord(resultSet);

                return word;
            }

        }catch(SQLException e){
            throw new SQLException("Failed to fetch words", e);
        }

        return null;
    }

    /**
     * Mengubah data dari ResultSet menjadi objek Word.
     *
     * @param resultSet hasil query dari database
     * @return objek Word yang sudah dipetakan dari data database
     * @throws SQLException jika terjadi kesalahan saat membaca ResultSet
     */
    private static Word mapToWord(ResultSet resultSet) throws SQLException {
        Word word = new Word();
        word.setId(resultSet.getLong("id"));
        word.setIndonesia(resultSet.getString("indonesia"));
        word.setEnglish(resultSet.getString("english"));
        return word;
    }


}

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

@Auto
public class DictionaryRepository {

    private final Connection connection;


    public DictionaryRepository() throws SQLException {
        this.connection = SqliteDatabaseImp.getConnection();
    }

    public List<Word> findAll() throws SQLException {
        List<Word> words = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(DictionaryQuery.FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Word word = mapToWord(resultSet);

                words.add(word);
            }
        }catch(SQLException e){
            throw new SQLException(e.getMessage());
        }

        return words;
    }

    public void insert(Word word) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(DictionaryQuery.INSERT)) {
            statement.setString(1, word.getIndonesia());
            statement.setString(2, word.getEnglish());

            statement.executeUpdate();



        }catch(SQLException e){
            throw new SQLException(e.getMessage());
        }

    }

    public Word findByIndonesia(String indonesia) throws SQLException {

        try(PreparedStatement statement = connection.prepareStatement(DictionaryQuery.FIND_BY_INDONESIA)) {
            statement.setString(1, indonesia);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Word word = mapToWord(resultSet);
                return word;
            }
        }catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
        return null;
    }

    public Word findByEnglish(String english) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(DictionaryQuery.FIND_BY_ENGLISH)) {
            statement.setString(1, english);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Word word = mapToWord(resultSet);

                return word;
            }

        }catch(SQLException e){
            throw new SQLException(e.getMessage());
        }

        return null;
    }

    private static Word mapToWord(ResultSet resultSet) throws SQLException {
        Word word = new Word();
        word.setId(resultSet.getLong("id"));
        word.setIndonesia(resultSet.getString("indonesia"));
        word.setEnglish(resultSet.getString("english"));
        return word;
    }


}

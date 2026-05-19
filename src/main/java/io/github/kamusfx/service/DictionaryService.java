package io.github.kamusfx.service;

import io.github.kamusfx.model.Word;

import java.sql.SQLException;
import java.util.HashMap;

public interface DictionaryService {

    void loadDictionary() throws SQLException;

    void insert(Word word) throws SQLException;

    String translateToEnglish(String indonesian);

    String translateToIndonesian(String english);

    String translateSentenceToEnglish(String sentence);

    String translateSentenceToIndonesian(String sentence);
}
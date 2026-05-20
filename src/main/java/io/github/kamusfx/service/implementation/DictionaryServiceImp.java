package io.github.kamusfx.service.implementation;

import io.github.kamusfx.MiniContainer.Auto;
import io.github.kamusfx.model.Word;
import io.github.kamusfx.repository.Dictionary.DictionaryRepository;
import io.github.kamusfx.service.DictionaryService;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


@Auto
@RequiredArgsConstructor
public class DictionaryServiceImp implements DictionaryService {

    private final HashMap<String, String> IndoToEnglish =  new HashMap<>();
    private final HashMap<String, String> EnglishToIndo = new HashMap<>();

    private final DictionaryRepository dictionaryRepository;

    @Override
    public void loadDictionary() throws SQLException {
        List<Word> dictionaries = dictionaryRepository.findAll();
        System.out.println("Loading Dictionary....");

        dictionaries.forEach(dictionary -> {
            IndoToEnglish.put(dictionary.getIndonesia(), dictionary.getEnglish());
            EnglishToIndo.put(dictionary.getEnglish(), dictionary.getIndonesia());
        });
        //logEntry();
        System.out.println("Dictionary Loaded Successfully");
    }

//    private void logEntry() {
//        System.out.println("===== Dictionaries Loaded In Indonesia ======");
//        for (String key : IndoToEnglish.keySet()) {
//            System.out.println(key + " : " + IndoToEnglish.get(key));
//        }
//        System.out.println("===== Dictionaries Loaded In English ======");
//        for (String key : EnglishToIndo.keySet()) {
//            System.out.println(key + " : " + EnglishToIndo.get(key));
//        }
//    }

    @Override
    public void insert(Word word) throws SQLException {
        dictionaryRepository.insert(word);
    }

    @Override
    public String translateToEnglish(String indonesian) {
        String result = IndoToEnglish.get(indonesian);
        return result;
    }

    @Override
    public String translateToIndonesian(String english) {
        String result = EnglishToIndo.get(english);
        return result;
    }

    @Override
    public String translateSentenceToEnglish(String sentence) {
        return "";
    }

    @Override
    public String translateSentenceToIndonesian(String sentence) {
        return "";
    }

}

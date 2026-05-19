package io.github.kamusfx.controller;

import io.github.kamusfx.MiniContainer.Auto;
import io.github.kamusfx.service.DictionaryService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@Auto
@RequiredArgsConstructor
public class HomeController {


    @FXML
    private ToggleButton indoToggle;
    @FXML
    private ToggleButton engToggle;

    @FXML
    private TextField sidePromptField;
    @FXML
    private Button sideSearchButton;

    @FXML
    private TextArea quickSearchResult;


    @FXML
    private TextArea inputArea;

    @FXML
    private Button translateButton;

    @FXML
    private TextArea resultArea;

    private final DictionaryService dictionaryService;
    boolean indo;
    boolean eng;


    public void initialize() throws SQLException {
        ToggleGroup langGroup = new ToggleGroup();
        indoToggle.setToggleGroup(langGroup);
        engToggle.setToggleGroup(langGroup);

        indoToggle.setSelected(true);
        dictionaryService.loadDictionary();
        setLang(true);

        Platform.runLater(() -> sidePromptField.requestFocus());

        setLang(langGroup);
        wordtTranslateButton();
    }

    private void setLang(ToggleGroup langGroup) {
        langGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                if (oldValue != null) {
                    oldValue.setSelected(true);
                }
            } else {
                setLang(newValue == indoToggle);
            }
        });
    }

    private void setLang(boolean indoSelected) {
        this.indo = indoSelected;
        this.eng = !indoSelected;
        if (indoSelected) {
            indoToggle.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-border-radius: 6; -fx-border-color: #4a90e2; -fx-pref-width: 60; -fx-pref-height: 30;");
            engToggle.setStyle("-fx-background-color: transparent; -fx-text-fill: #4a90e2; -fx-font-weight: bold; -fx-background-radius: 6; -fx-border-radius: 6; -fx-border-color: #4a90e2; -fx-pref-width: 60; -fx-pref-height: 30;");
        } else {
            engToggle.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-border-radius: 6; -fx-border-color: #4a90e2; -fx-pref-width: 60; -fx-pref-height: 30;");
            indoToggle.setStyle("-fx-background-color: transparent; -fx-text-fill: #4a90e2; -fx-font-weight: bold; -fx-background-radius: 6; -fx-border-radius: 6; -fx-border-color: #4a90e2; -fx-pref-width: 60; -fx-pref-height: 30;");
        }
    }

    public void wordtTranslateButton(){
        sideSearchButton.setOnAction(event -> {
            translate();
        });
        sidePromptField.setOnAction(event -> {
            translate();
        });
    }

    public void translate() {
        String text = sidePromptField.getText();
        System.out.println("Kata yang dicari : " + text);
        if (text == null || text.trim().isEmpty()) {
            return;
        }
        String cleanedText = text.trim().toLowerCase();
        
        String translation;
        if (this.indo) {
            System.out.println("Menerjemahkan dari Indonesia ke Inggris...");
            translation = dictionaryService.translateToIndonesian(cleanedText);
        } else {
            System.out.println("Menerjemahkan dari Inggris ke Indonesia...");
            translation = dictionaryService.translateToEnglish(cleanedText);
        }

        System.out.println("Hasil terjemahan : " + translation);

        if (translation == null || translation.isEmpty()) {
            translation = "Kata tidak ditemukan";
        }

        quickSearchResult.setText(translation);
    }
}

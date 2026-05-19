package io.github.kamusfx.controller;

import io.github.kamusfx.MiniContainer.Auto;
import io.github.kamusfx.service.DictionaryService;
import io.github.kamusfx.service.implementation.AuthService;
import io.github.kamusfx.view.FxmlPath;
import io.github.kamusfx.view.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

import javafx.stage.Stage;

@Auto
@RequiredArgsConstructor
public class LoginController {

    @FXML
    private TextField loginUsernameField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Label loginErrorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button goToRegisterButton;

    private final AuthService authService;

    @FXML
    public void initialize() {
        System.out.println("initializing....Login Controller");
        Platform.runLater(() -> loginUsernameField.requestFocus());
        loginUsernameField.setOnAction(e -> loginPasswordField.requestFocus());
        loginPasswordField.setOnAction(e -> loginButton.fire());
        login();
        goToRegister();
    }

    public void login() {
        loginButton.setOnAction(event -> {
            try {
                if (authService.login(loginUsernameField.getText(), loginPasswordField.getText())) {
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(SceneManager.getScene(FxmlPath.HOME_PATH));
                } else {
                    loginErrorLabel.setText("Username atau password salah.");
                    loginErrorLabel.setVisible(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void goToRegister() {
        goToRegisterButton.setOnAction(
                event -> {
                    Stage stage = (Stage) goToRegisterButton.getScene().getWindow();
                    stage.setScene(SceneManager.getScene(FxmlPath.REGISTER_PATH));
                });
    }

}

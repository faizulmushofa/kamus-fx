package io.github.kamusfx.controller;

import io.github.kamusfx.MiniContainer.Auto;
import io.github.kamusfx.service.implementation.AuthService;
import io.github.kamusfx.view.FxmlPath;
import io.github.kamusfx.view.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@Auto
@RequiredArgsConstructor
public class RegisterController {

    @FXML
    private TextField registerUsernameField;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private PasswordField registerConfirmPasswordField;

    @FXML
    private Label registerErrorLabel;

    @FXML
    private Button registerButton;

    @FXML
    private Button goToLoginButton;


    private final AuthService authService;

    public void initialize() throws SQLException {
        Platform.runLater(() -> registerUsernameField.requestFocus());
        handleGoToLoginButtonAction();
        handleRegisterButtonAction();
        autoclick();
    }

    public void autoclick(){
        registerUsernameField.setOnAction(event -> {
            registerPasswordField.requestFocus();
        });
        registerPasswordField.setOnAction(event -> {
            registerConfirmPasswordField.requestFocus();
        });
        registerConfirmPasswordField.setOnAction(event -> {
            registerButton.fire();
        });
    }

    public void handleRegisterButtonAction() throws SQLException {
        registerButton.setOnAction(
                event -> {
                    System.out.println("Register button pressed");
                    try {
                        register();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }



    protected void register() throws SQLException {
        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();
        String confirmPassword = registerConfirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            registerErrorLabel.setText("Username and/or password are empty");
            registerErrorLabel.setVisible(true);
            return;
        }
        if (!password.equals(confirmPassword)) {
            registerErrorLabel.setText("Passwords do not match");
            registerErrorLabel.setVisible(true);
            return;
        }

        if (authService.register(username, password, confirmPassword)){
            Stage stage = (Stage) goToLoginButton.getScene().getWindow();
            stage.setScene(SceneManager.getScene(FxmlPath.LOGIN_PATH));
        } else {
            registerErrorLabel.setText("Registration failed");
            registerErrorLabel.setVisible(true);
        }

    }

    public void handleGoToLoginButtonAction() throws SQLException {
        goToLoginButton.setOnAction(
                event -> {
                    System.out.println("Go to login button pressed");
                    Stage stage = (Stage) goToLoginButton.getScene().getWindow();
                    stage.setScene(SceneManager.getScene(FxmlPath.LOGIN_PATH));
                }
        );
    }
}

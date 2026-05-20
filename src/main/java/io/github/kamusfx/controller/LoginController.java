package io.github.kamusfx.controller;

import io.github.kamusfx.MiniContainer.Auto;
import io.github.kamusfx.model.Session;
import io.github.kamusfx.service.implementation.AuthService;
import io.github.kamusfx.service.implementation.SessionService;
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

/**
 * Controller untuk halaman login pengguna pada aplikasi Kamus FX.
 *
 * Bertanggung jawab menangani interaksi UI pada form login,
 * termasuk validasi input, pemanggilan service autentikasi,
 * serta navigasi ke halaman home setelah login berhasil.
 *
 * Controller ini di-manage oleh MiniContainer (@Auto) dan menggunakan
 * AuthService sebagai dependency utama untuk proses registrasi.
 *
 * @author its_me20
 * @version 1.0
 * @since 2026 - 05 -20
 */
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
        Platform.runLater(() -> loginUsernameField.requestFocus());
        loginUsernameField.setOnAction(e -> loginPasswordField.requestFocus());
        loginPasswordField.setOnAction(e -> loginButton.fire());
        login();
        goToRegister();
    }

    /**
     * Method memproses login ketika tombol Login di tekan
     *
     *
     * @throws RuntimeException apabila terjadi kesalahan dalam proses login
     * @throws SQLException apabila terjadi kesalahan dengan pengolahan data dari database
     */
    public void login() {
        loginButton.setOnAction(event -> {
            try {
                if (authService.login(loginUsernameField.getText(), loginPasswordField.getText())) {
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    SessionService.log("Login Button pressed","Logged in into Home");
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

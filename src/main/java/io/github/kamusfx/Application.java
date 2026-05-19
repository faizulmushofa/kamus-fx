package io.github.kamusfx;

import io.github.kamusfx.MiniContainer.BeanManager;
import io.github.kamusfx.view.FxmlPath;
import io.github.kamusfx.view.SceneManager;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {

        BeanManager beanManager = new BeanManager();
        SceneManager.setBeanManager(beanManager);
        Scene scene = SceneManager.getScene(FxmlPath.LOGIN_PATH);
        stage.setScene(scene);
        stage.setTitle("Kamus App");
        stage.show();
    }
}

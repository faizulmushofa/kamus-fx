package io.github.kamusfx.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.util.HashMap;
import java.util.Map;
import io.github.kamusfx.MiniContainer.BeanManager;

public class SceneManager {

    private static final Map<String, Scene> scenes = new HashMap<>();
    private static BeanManager beanManager;

    public static void setBeanManager(BeanManager bm) {
        beanManager = bm;
    }

    public static Scene getScene(String fxmlPath) {
        return scenes.computeIfAbsent(fxmlPath, p -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        SceneManager.class.getResource(p)
                );

                if (beanManager != null) {
                    loader.setControllerFactory(beanManager::getBean);
                }

                return new Scene(loader.load());

            } catch (Exception e) {
                throw new RuntimeException("Failed to load FXML: " + p, e);
            }
        });
    }
}
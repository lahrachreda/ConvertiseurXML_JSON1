package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Chargement du fichier FXML (Vue)
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view.fxml"));
        
        // Création de la scène (fenêtre)
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        
        stage.setTitle("Convertisseur XML <-> JSON");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
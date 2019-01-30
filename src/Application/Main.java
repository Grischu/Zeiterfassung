package Application;

import Controller.ScreenController;
import Database.Datenbank;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.Application;

/**
 * Main-Klasse um die Applikation zu starten.
 */
public class Main extends Application {

    private static ScreenController screenController;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(400);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("View/Startfenster.fxml"));

        AnchorPane pane = loader.load();

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Zeiterfassung");
        screenController = new ScreenController(scene);
        Datenbank datenBank = new Datenbank();
    }

    public static ScreenController getScreenController() {
        return screenController;
    }


    public static void main(String[] args) {
        launch(args);
    }
}

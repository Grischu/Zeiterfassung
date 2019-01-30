package Controller;

import java.io.IOException;
import java.util.HashMap;

import Application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Handler für das switchen der Fenster.
 */
public class ScreenController {
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;

    public ScreenController(Scene main) {
        this.main = main;
    }

    public void addScreen(String name, Pane pane){
         screenMap.put(name, pane);
    }

    public void activate(String name){
        main.setRoot( screenMap.get(name) );
    }
    
	public void zeiterfassungOeffnen() {
		ScreenController screenController = Main.getScreenController();
		try {
			screenController.addScreen("Zeiterfassung", FXMLLoader.load(getClass().getResource("../View/Zeiterfassung.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		screenController.activate("Zeiterfassung");
	}

}
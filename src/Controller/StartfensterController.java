package Controller;

import Application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class StartfensterController implements ControllerInterface {

	@FXML
    private Button loginButton;

	@FXML
	private TextField benutzerName;

	@FXML
    private PasswordField passwort;
	
	@FXML
    public void initialize () {



		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
					Main.getScreenController().zeiterfassungOeffnen();
				}
		});
    }
	
	

}
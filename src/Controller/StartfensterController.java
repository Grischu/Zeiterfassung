package Controller;

import Application.Main;
import Database.LoginDAO;
import Interface.ControllerInterface;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
	private JFXTextField benutzerName;

	@FXML
    private JFXPasswordField passwort;
	
	@FXML
    public void initialize () {
        setLoginButton();
    }

    private void setLoginButton() {
        loginButton.setOnAction(event -> {
            int user = LoginDAO.login(benutzerName.getText(), passwort.getText());
            if(user != 0) {
                Main.getScreenController().zeiterfassungOeffnen();

            }
        });
    }
}
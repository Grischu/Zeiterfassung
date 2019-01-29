package Controller;

import Application.LoginApp;
import Application.Main;
import Database.LoginDAO;
import Interface.ControllerInterface;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
                LoginApp.setUser(user);
                Main.getScreenController().zeiterfassungOeffnen();
            }
        });
    }
}
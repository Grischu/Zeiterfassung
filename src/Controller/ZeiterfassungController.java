package Controller;

import Application.Main;
import Database.Datenbank;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class ZeiterfassungController {

	Datenbank datenBank = new Datenbank();

	@FXML
	private HBox hBox;

	@FXML
    private DatePicker kalender;

	@FXML
    private Button letzterMonat;

	@FXML
    private Button naechsterMonat;

	@FXML
	private Label aktuellerMonat;

	@FXML
	private TextField zeiterfassung;

	private String aktuellerTag;

	private String aktuellesJahr;

	@FXML
	private void initialize () {

		List<Integer> dates = new ArrayList<Integer>();

		//TODO thisMonth.minusMonths(1);
		//TODO thisMonth.plusMonths(1);

		// Get the number of days in that month
		YearMonth yearMonthObject = YearMonth.now();
		int daysInMonth = yearMonthObject.lengthOfMonth();

		aktuellerMonat.setText(yearMonthObject.getMonth().toString());

		LocalDate localDate = LocalDate.now();

		aktuellerTag =  Integer.toString(localDate.getDayOfMonth());

		aktuellesJahr = Integer.toString(localDate.getYear());

		ToggleGroup toggleGroup = new ToggleGroup();

		for(int i = 1; i<=daysInMonth;i++) {

		    ToggleButton button = new ToggleButton(Integer.toString(i));

		    button.setToggleGroup(toggleGroup);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
					//button.setStyle("-fx-base: red;");
                    aktuellerTag = button.getText();
                    System.out.println(aktuellerTag);
                }
            });

			hBox.getChildren().addAll(button);
			System.out.println(i);
		}




	}
}
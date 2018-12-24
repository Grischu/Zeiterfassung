package Controller;

import Database.Datenbank;
import Database.ZeiterfassungDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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

	/*@FXML
    private Label aktuellesDatum;
*/
	@FXML
	private TextField zeiterfassungField;

	@FXML
	private Button erfassenButton;

	private int aktuellerTag;

	@FXML
    private Label aktuellerMonat;

	private int aktuellesJahr;

	@FXML
	private void initialize () {



		List<Integer> dates = new ArrayList<Integer>();

		//TODO thisMonth.minusMonths(1);
		//TODO thisMonth.plusMonths(1);

		// Get the number of days in that month
		YearMonth yearMonthObject = YearMonth.now();
		int daysInMonth = yearMonthObject.lengthOfMonth();

		aktuellerMonat.setText(Integer.toString(yearMonthObject.getMonth().getValue()));

        //aktuellesDatum.setText(aktuellerMonat.getText());

		LocalDate localDate = LocalDate.now();

		aktuellerTag =  localDate.getDayOfMonth();

		aktuellesJahr = localDate.getYear();

		setToggleButtons(daysInMonth);

        setMonateHandler();

		erfassenButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GregorianCalendar calendar = new GregorianCalendar(aktuellesJahr,Integer.parseInt(aktuellerMonat.getText()),aktuellerTag);
				Date date = calendar.getTime();
				ZeiterfassungDAO.updateZeiterfassung(date, 1, Integer.parseInt(zeiterfassungField.getText()));
			}
		});
	}

    private void setMonateHandler() {
        letzterMonat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(aktuellerMonat.getText().equals("1")) {
                    aktuellerMonat.setText("12");
                    aktuellesJahr = aktuellesJahr-1;
                }
                else {
                    aktuellerMonat.setText(Integer.toString(Integer.parseInt(aktuellerMonat.getText())-1));
                }
            }
        });

        naechsterMonat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(aktuellerMonat.getText().equals("12")) {
                    aktuellerMonat.setText("1");
                    aktuellesJahr = aktuellesJahr+1;
                }
                else {
                    aktuellerMonat.setText(Integer.toString(Integer.parseInt(aktuellerMonat.getText())+1));
                }
            }
        });
    }

    private void setToggleButtons(int daysInMonth) {
		ToggleGroup toggleGroup = new ToggleGroup();

		for(int i = 1; i<=daysInMonth;i++) {

		    ToggleButton button = new ToggleButton(Integer.toString(i));

		    button.setToggleGroup(toggleGroup);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
					//button.setStyle("-fx-base: red;");
                    aktuellerTag = Integer.parseInt(button.getText());

                    GregorianCalendar calendar = new GregorianCalendar(aktuellesJahr,Integer.parseInt(aktuellerMonat.getText()),aktuellerTag);
                    Date date = calendar.getTime();

                    zeiterfassungField.setText(Integer.toString(ZeiterfassungDAO.getZeiterfassung(date, 1)));

                }
            });
			hBox.getChildren().addAll(button);
		}

		ToggleButton toggleButton = (ToggleButton) hBox.getChildren().get(aktuellerTag-1);
		toggleButton.setSelected(true);
	}
}
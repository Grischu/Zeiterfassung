package Controller;

import Database.Datenbank;
import Database.ZeiterfassungDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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

	@FXML
    private Label aktuellesDatum;

	@FXML
	private TextField zeiterfassungField;

	@FXML
	private Button erfassenButton;

	private int aktuellerTag;
    private int aktuellerMonat;
	private int aktuellesJahr;

	@FXML
	private void initialize () {

		// Get the number of days in that month
        YearMonth yearMonthObject = YearMonth.now();
		int daysInMonth = yearMonthObject.lengthOfMonth();
		aktuellerMonat = yearMonthObject.getMonth().getValue();
		LocalDate localDate = LocalDate.now();
		aktuellerTag =  localDate.getDayOfMonth();
		aktuellesJahr = localDate.getYear();

		setToggleButtons(daysInMonth);
        setMonateHandler();

		erfassenButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
                updateZeitFeld();
			}
		});
	}

    private void setMonateHandler() {
        letzterMonat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(aktuellerMonat == 1) {
                    aktuellerMonat = 12;
                    aktuellesJahr = aktuellesJahr-1;
                }
                else {
                    aktuellerMonat = aktuellerMonat-1;
                }
                aktuellesDatum.setText(Integer.toString(aktuellerTag) + ". " + Integer.toString(aktuellerMonat) + " " + Integer.toString(aktuellesJahr));
                updateZeitFeld();

                YearMonth yearMonthObject = YearMonth.of(aktuellesJahr, aktuellerMonat);
                aktuellerTag = 1;
                setToggleButtons(yearMonthObject.lengthOfMonth());
            }
        });

        naechsterMonat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(aktuellerMonat == 12) {
                    aktuellerMonat = 1;
                    aktuellesJahr = aktuellesJahr+1;
                }
                else {
                    aktuellerMonat = aktuellerMonat+1;
                }
                aktuellesDatum.setText(Integer.toString(aktuellerTag) + ". " + Integer.toString(aktuellerMonat) + " " + Integer.toString(aktuellesJahr));
                updateZeitFeld();

                YearMonth yearMonthObject = YearMonth.of(aktuellesJahr, aktuellerMonat);
                aktuellerTag = 1;
                setToggleButtons(yearMonthObject.lengthOfMonth());
            }
        });

    }

    private void setToggleButtons(int daysInMonth) {

	    hBox.getChildren().clear();

		ToggleGroup toggleGroup = new ToggleGroup();

		for(int i = 1; i<=daysInMonth;i++) {

		    ToggleButton button = new ToggleButton(Integer.toString(i));

		    button.setToggleGroup(toggleGroup);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
					//button.setStyle("-fx-base: red;");
                    aktuellerTag = Integer.parseInt(button.getText());

                    getZeiterfassung();
                    aktuellesDatum.setText(Integer.toString(aktuellerTag) + ". " + Integer.toString(aktuellerMonat) + " " + Integer.toString(aktuellesJahr));
                }
            });
			hBox.getChildren().addAll(button);
		}
        hBox.setAlignment(Pos.CENTER);
		ToggleButton toggleButton = (ToggleButton) hBox.getChildren().get(aktuellerTag-1);
		toggleButton.setSelected(true);

        getZeiterfassung();
        aktuellesDatum.setText(Integer.toString(aktuellerTag) + ". " + Integer.toString(aktuellerMonat) + " " + Integer.toString(aktuellesJahr));
	}

    private void updateZeitFeld() {
        GregorianCalendar calendar = new GregorianCalendar(aktuellesJahr,aktuellerMonat,aktuellerTag);
        Date date = calendar.getTime();

        ZeiterfassungDAO.updateZeiterfassung(date, 1, Integer.parseInt(zeiterfassungField.getText()));
    }

    private void getZeiterfassung() {
        GregorianCalendar calendar = new GregorianCalendar(aktuellesJahr,aktuellerMonat,aktuellerTag);
        Date date = calendar.getTime();

        zeiterfassungField.setText(Integer.toString(ZeiterfassungDAO.getZeiterfassung(date, 1)));
    }


    //TODO mehr als eine Zeiterfassung pro Tag Möglich
    //TODO Erfassungen beim Tag anzeigen

}
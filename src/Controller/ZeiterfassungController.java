package Controller;

import Application.LoginApp;
import Database.ZeiterfassungDAO;
import Interface.ControllerInterface;
import Model.ActionButtonTableCell;
import Model.Buchung;
import Model.MonatEnum;
import Model.Zeiterfassung;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.GregorianCalendar;

public class ZeiterfassungController implements ControllerInterface {
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
	@FXML
    private ChoiceBox<Buchung> buchung;
	@FXML
    private TextField pause;
	@FXML
    private TextField beschreibung;
	@FXML
    private TableView<Zeiterfassung> erfassungTable = new TableView<Zeiterfassung>();
	@FXML
    private TableColumn<Zeiterfassung, String> buchungColumn;
    @FXML
    private TableColumn<Zeiterfassung, Double> zeitColumn;
    @FXML
    private TableColumn<Zeiterfassung, Integer> beschreibungColumn;
    @FXML
    private TableColumn aktionColumn;
    @FXML
    private Label zeitField;

	private int aktuellerTag;
    private int aktuellerMonat;
	private int aktuellesJahr;

	@FXML
	public void initialize () {
	    aktuellesDatumInitialisieren();
        setMonateHandler();
        setBuchung();
        setBuchungsTable();
        setErfassenButton();
    }

    private void aktuellesDatumInitialisieren() {
        YearMonth yearMonthObject = YearMonth.now();
        int daysInMonth = yearMonthObject.lengthOfMonth();
        aktuellerMonat = yearMonthObject.getMonth().getValue();
        LocalDate localDate = LocalDate.now();
        aktuellerTag =  localDate.getDayOfMonth();
        aktuellesJahr = localDate.getYear();

        setToggleButtons(daysInMonth);
    }

    private void setErfassenButton() {
        erfassenButton.setOnAction(event -> {
            updateZeit();
            setZeitField();
            setBuchungsTable();
        });
    }

    private void setMonateHandler() {
        letzterMonat.setOnAction(event -> {
            if(aktuellerMonat == 1) {
                aktuellerMonat = 12;
                aktuellesJahr = aktuellesJahr-1;
            }
            else {
                aktuellerMonat = aktuellerMonat-1;
            }
            monatWechseln();
        });

        naechsterMonat.setOnAction(event -> {
            if(aktuellerMonat == 12) {
                aktuellerMonat = 1;
                aktuellesJahr = aktuellesJahr+1;
            }
            else {
                aktuellerMonat = aktuellerMonat+1;
            }
            monatWechseln();
        });

    }

    private void monatWechseln() {
        aktuellerTag = 1;
        aktuellesDatum.setText(Integer.toString(aktuellerTag) + ". " + MonatEnum.getFromId(aktuellerMonat) + " " + Integer.toString(aktuellesJahr));

        setZeitField();
        setBuchungsTable();
        YearMonth yearMonthObject = YearMonth.of(aktuellesJahr, aktuellerMonat);
        setToggleButtons(yearMonthObject.lengthOfMonth());
    }

    private void setToggleButtons(int daysInMonth) {
	    hBox.getChildren().clear();
		ToggleGroup toggleGroup = new ToggleGroup();

		for(int i = 1; i<=daysInMonth;i++) {
		    ToggleButton button = new ToggleButton(Integer.toString(i));
		    button.setToggleGroup(toggleGroup);
            button.setOnAction(event -> {
                //button.setStyle("-fx-base: red;");
                aktuellerTag = Integer.parseInt(button.getText());
                setBuchungsTable();
                setZeitField();
                aktuellesDatum.setText(Integer.toString(aktuellerTag) + ". " + MonatEnum.getFromId(aktuellerMonat) + " " + Integer.toString(aktuellesJahr));
            });
			hBox.getChildren().addAll(button);
		}
        hBox.setAlignment(Pos.CENTER);
		ToggleButton toggleButton = (ToggleButton) hBox.getChildren().get(aktuellerTag-1);
		toggleButton.setSelected(true);
        setZeitField();

        aktuellesDatum.setText(Integer.toString(aktuellerTag) + ". " + MonatEnum.getFromId(aktuellerMonat) + " " + Integer.toString(aktuellesJahr));
	}

    private void updateZeit() {
        GregorianCalendar calendar = new GregorianCalendar(aktuellesJahr,aktuellerMonat,aktuellerTag);
        Date date = calendar.getTime();

        ZeiterfassungDAO.updateZeiterfassung(date, LoginApp.getUser(), Double.parseDouble(zeiterfassungField.getText()), buchung.getValue().getId());
    }

    private void setZeitField() {
        GregorianCalendar calendar = new GregorianCalendar(aktuellesJahr,aktuellerMonat,aktuellerTag);
        Date date = calendar.getTime();
        zeitField.setText(Double.toString(ZeiterfassungDAO.getZeiterfassung(date, LoginApp.getUser())));
    }

    private void setBuchung() {
	    buchung.setItems(ZeiterfassungDAO.getBuchungen());
        buchung.setConverter(new StringConverter<Buchung>() {
            //Wichtig für darstellung
            @Override
            public String toString(Buchung buchung) {
                return buchung.getBuchungName().getValue();
            }
            //Nicht gebraucht
            @Override
            public Buchung fromString(String string) {
                return null;
            }
        });
        buchung.getSelectionModel().selectFirst();
    }

    private void setBuchungsTable() {
        GregorianCalendar calendar = new GregorianCalendar(aktuellesJahr,aktuellerMonat,aktuellerTag);
        Date date = calendar.getTime();

        zeitColumn.setCellValueFactory(cellData -> cellData.getValue().getZeit().asObject());
        buchungColumn.setCellValueFactory(cellData -> cellData.getValue().getBuchung().getBuchungName());
        beschreibungColumn.setCellValueFactory(cellData -> cellData.getValue().getDatum().asObject()); //TODO Beschreibung

        //Dem Button die Logik für das Löschen hinzufügen
        aktionColumn.setCellFactory(ActionButtonTableCell.<Zeiterfassung>forTableColumn("Löschen", (Zeiterfassung zeiterfassung) -> {
            erfassungTable.getItems().remove(zeiterfassung);
            ZeiterfassungDAO.zeiterfassungLoeschen(zeiterfassung.getId().get());
            setZeitField();
            return zeiterfassung;
        }));

        erfassungTable.setItems(ZeiterfassungDAO.getErfassungen(date, LoginApp.getUser()));
        erfassungTable.getSortOrder().add(buchungColumn);
    }

    //TODO Login mit user übergeben
    //TODO Beschreibung -> nicht wichtig
    //TODO "Heute" -> nicht wichtig
    //TODO On action im fxml definieren nicht wichtig
    //TODO Sortierung Buchung -> Nicht wichtig
    //TODO Pause -> Nicht wichtig
    //TODO Kalender zum funktionieren bringen - > Nicht wichtig
    //TODO Farben wenn erfasst oder nicht -> Nicht wichtig
    //TODO Maven
}
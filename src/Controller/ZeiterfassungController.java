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

/**
 * Controller für das Zeiterfassungsfenster.
 */
public class ZeiterfassungController implements ControllerInterface {

    //FXML Variablen
	@FXML
	private HBox hBox;
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
    private TextField beschreibung;
	@FXML
    private TableView<Zeiterfassung> erfassungTable = new TableView<Zeiterfassung>();
	@FXML
    private TableColumn<Zeiterfassung, String> buchungColumn;
    @FXML
    private TableColumn<Zeiterfassung, Double> zeitColumn;
    @FXML
    private TableColumn<Zeiterfassung, String> beschreibungColumn;
    @FXML
    private TableColumn aktionColumn;
    @FXML
    private Label zeitField;

    //Hilfsvariablen
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

    /**
     * Beim ersten öffnen das aktuelle Datum setzen.
     */
    private void aktuellesDatumInitialisieren() {
        YearMonth yearMonthObject = YearMonth.now();
        int daysInMonth = yearMonthObject.lengthOfMonth();
        aktuellerMonat = yearMonthObject.getMonth().getValue();
        LocalDate localDate = LocalDate.now();
        aktuellerTag =  localDate.getDayOfMonth();
        aktuellesJahr = localDate.getYear();

        setToggleButtons(daysInMonth);
    }

    /**
     * Den Erfassen-Button inizialisieren
     */
    private void setErfassenButton() {
        erfassenButton.setOnAction(event -> {
            updateZeit();
            setZeitField();
            setBuchungsTable();
        });
    }

    /**
     * Die Logik für die Buttons 'nächster monat' und 'vorheriger monat' setzen.
     */
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

    /**
     * Wird beim Monatswechsel aufgerufen und setzt den aktuellen Tag auf 1 und aktualisiert
     * das angezeigte Datum.
     */
    private void monatWechseln() {
        aktuellerTag = 1;
        aktuellesDatum.setText(aktuellerTag + ". " + MonatEnum.getFromId(aktuellerMonat) + " " + aktuellesJahr);

        setZeitField();
        setBuchungsTable();
        YearMonth yearMonthObject = YearMonth.of(aktuellesJahr, aktuellerMonat);
        setToggleButtons(yearMonthObject.lengthOfMonth());
    }

    /**
     * Logik für die Tage-Buttons und das hinzufügen in die hBox welche angezeigt wird.
     * @param daysInMonth Tage im Monat
     */
    private void setToggleButtons(int daysInMonth) {
	    hBox.getChildren().clear();
		ToggleGroup toggleGroup = new ToggleGroup();
        //Für jeden Tag im Monat einen neuen Button erstellen und der hBox hinzufügen
		for(int i = 1; i<=daysInMonth;i++) {
		    ToggleButton button = new ToggleButton(Integer.toString(i));
		    button.setToggleGroup(toggleGroup);
		    //Action, dass beim Tageswechsel der Text aktualisiert wird
            button.setOnAction(event -> {
                aktuellerTag = Integer.parseInt(button.getText());
                setBuchungsTable();
                setZeitField();
                aktuellesDatum.setText(aktuellerTag + ". " + MonatEnum.getFromId(aktuellerMonat) + " " + aktuellesJahr);
            });
			hBox.getChildren().addAll(button);
		}
        hBox.setAlignment(Pos.CENTER);
		ToggleButton toggleButton = (ToggleButton) hBox.getChildren().get(aktuellerTag-1);
		toggleButton.setSelected(true);
        setZeitField();

        aktuellesDatum.setText(aktuellerTag + ". " + MonatEnum.getFromId(aktuellerMonat) + " " + aktuellesJahr);
	}

    /**
     * Die erfasste Zeit auf der Datenbank aktualisieren
     */
    private void updateZeit() {
        GregorianCalendar calendar = new GregorianCalendar(aktuellesJahr,aktuellerMonat,aktuellerTag);
        Date date = calendar.getTime();

        ZeiterfassungDAO.updateZeiterfassung(date, LoginApp.getUser(), Double.parseDouble(zeiterfassungField.getText()), buchung.getValue().getId(), beschreibung.getText());
    }

    /**
     * Den 'Zeit gearbeitet heute' Text aktualisieren
     */
    private void setZeitField() {
        GregorianCalendar calendar = new GregorianCalendar(aktuellesJahr,aktuellerMonat,aktuellerTag);
        Date date = calendar.getTime();
        zeitField.setText(Double.toString(ZeiterfassungDAO.getZeiterfassung(date, LoginApp.getUser())));
    }

    /**
     * Die Choicebox mit den Buchungstypen aus der DB abfüllen und die logik der Darstellung setzen
     */
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

    /**
     * Die logik der Buchungstabelle setzen und aus der DB Daten für die Anzeige holen.
     */
    private void setBuchungsTable() {
        GregorianCalendar calendar = new GregorianCalendar(aktuellesJahr,aktuellerMonat,aktuellerTag);
        Date date = calendar.getTime();

        zeitColumn.setCellValueFactory(cellData -> cellData.getValue().getZeit().asObject());
        buchungColumn.setCellValueFactory(cellData -> cellData.getValue().getBuchung().getBuchungName());
        beschreibungColumn.setCellValueFactory(cellData -> cellData.getValue().getBeschreibung());

        //Dem Button die Logik für das Löschen hinzufügen
        aktionColumn.setCellFactory(ActionButtonTableCell.<Zeiterfassung>forTableColumn("Loeschen", (Zeiterfassung zeiterfassung) -> {
            erfassungTable.getItems().remove(zeiterfassung);
            ZeiterfassungDAO.zeiterfassungLoeschen(zeiterfassung.getId().get());
            setZeitField();
            return zeiterfassung;
        }));

        erfassungTable.setItems(ZeiterfassungDAO.getErfassungen(date, LoginApp.getUser()));
        erfassungTable.getSortOrder().add(buchungColumn);
    }

}
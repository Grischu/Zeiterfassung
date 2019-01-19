package Model;

import Application.Main;
import Controller.ZeiterfassungController;
import Database.ZeiterfassungDAO;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.Date;

public class Zeiterfassung {

    private IntegerProperty id;
    private Buchung buchung;
    private DoubleProperty zeit;
    private IntegerProperty datum;

    public Zeiterfassung() {
        this.id = new SimpleIntegerProperty();
        this.zeit = new SimpleDoubleProperty();
        this.datum = new SimpleIntegerProperty();
    }

    public IntegerProperty getId() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public DoubleProperty getZeit() {
        return zeit;
    }

    public void setZeit(double zeit) {
        this.zeit.set(zeit);
    }

    public IntegerProperty getDatum() {
        return datum;
    }

    public void setDatum(int datum) {
        this.datum.set(datum);
    }

    public Buchung getBuchung() {
        return buchung;
    }

    public void setBuchung(Buchung buchung) {
        this.buchung = buchung;
    }
}

package Model;

import javafx.beans.property.*;

/**
 * Zeiterfassungs-Model
 */
public class Zeiterfassung {

    private IntegerProperty id;
    private Buchung buchung;
    private DoubleProperty zeit;
    private LongProperty datum;
    private StringProperty beschreibung;

    public Zeiterfassung() {
        this.id = new SimpleIntegerProperty();
        this.zeit = new SimpleDoubleProperty();
        this.datum = new SimpleLongProperty();
        this.beschreibung = new SimpleStringProperty();
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

    public void setDatum(long datum) {
        this.datum.set(datum);
    }

    public StringProperty getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung.set(beschreibung);
    }

    public Buchung getBuchung() {
        return buchung;
    }

    public void setBuchung(Buchung buchung) {
        this.buchung = buchung;
    }
}

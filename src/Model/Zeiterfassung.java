package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Zeiterfassung {

    private IntegerProperty id;
    private IntegerProperty zeit;
    private IntegerProperty datum;

    public Zeiterfassung() {
        this.id = new SimpleIntegerProperty();
        this.zeit = new SimpleIntegerProperty();
        this.datum = new SimpleIntegerProperty();
    }

    public IntegerProperty getId() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty getZeit() {
        return zeit;
    }

    public void setZeit(int zeit) {
        this.zeit.set(zeit);
    }

    public IntegerProperty getDatum() {
        return datum;
    }

    public void setDatum(int datum) {
        this.datum.set(datum);
    }
}

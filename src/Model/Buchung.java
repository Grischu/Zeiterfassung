package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Buchung {
    private IntegerProperty id;
    private StringProperty buchungName;

    public Buchung() {
        this.id = new SimpleIntegerProperty();
        this.buchungName = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public StringProperty getBuchungName() {
        return buchungName;
    }

    public void setBuchungName(String buchungName) {
        this.buchungName.set(buchungName);
    }

}

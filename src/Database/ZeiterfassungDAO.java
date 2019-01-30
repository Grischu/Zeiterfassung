package Database;

import Model.Buchung;
import Model.Zeiterfassung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Data Access Object für die Zeiterfassung
 */
public class ZeiterfassungDAO {

    /**
     * Zeiterfassung auf der Datenbank updaten
     * @param date datum
     * @param user user
     * @param zeit erfasste zeit
     * @param buchungId buchungstyp
     * @param beschreibung beschreibung der erfassung
     */
    public static void updateZeiterfassung(Date date, int user, double zeit, int buchungId, String beschreibung) {
        String sql = "INSERT INTO Zeiterfassung (persid,zeit,datum,buchungId,beschreibung) values(" +
                user + "," + zeit + "," + date.getTime() + "," + buchungId  + "," + "'" + beschreibung + "'" +");";

        try (Connection conn = Datenbank.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Zeiterfassung von einem User zu einem Tag holen
     * @param date datum
     * @param user user
     * @return zeiterfassung
     */
    public static double getZeiterfassung(Date date, int user) {
        ResultSet rs = null;
        double result = 0;
        String sql = "SELECT * from zeiterfassung where datum=" + date.getTime() + " AND persId=" + user;

        try {
            rs = Datenbank.executeQuery(sql);
            while (rs.next()) {
                result += rs.getDouble("zeit");
            }
        } catch (Exception a) {
            System.out.println(a.toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * Die Buchungstypen aus der DB holen.
     * @return Liste aus buchungen
     */
    public static ObservableList<Buchung> getBuchungen() {
        ObservableList<Buchung> result = FXCollections.observableArrayList();
        ResultSet rs = null;
        try {
            rs = Datenbank.executeQuery("SELECT * FROM buchung");
            while (rs.next()) {
                Buchung buchung = new Buchung();
                buchung.setId(rs.getInt("ID"));
                buchung.setBuchungName(rs.getString("name"));
                result.add(buchung);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Alle Informationen zu einer Zeiterfassung zu einem bestimmten Datum und User holen (für Tabelle).
     * @param date datum
     * @param user user
     * @return liste aus Zeiterfassungen
     */
    public static ObservableList<Zeiterfassung> getErfassungen(Date date, int user) {
        ResultSet r = null;
        ObservableList<Zeiterfassung> result = FXCollections.observableArrayList();
        try {
            r = Datenbank.executeQuery("SELECT * from zeiterfassung where datum=" + convertUtilToSql(date).getTime() + " AND persId=" + user);
            while (r.next()) {
                Zeiterfassung zeiterfassung = new Zeiterfassung();
                Buchung buchung = new Buchung();
                buchung.setId(r.getInt("buchungId"));
                buchung.setBuchungName(getBuchungNameFromId(r.getInt("buchungId")));

                zeiterfassung.setBuchung(buchung);
                zeiterfassung.setId(r.getInt("id"));
                zeiterfassung.setZeit(r.getDouble("zeit"));
                zeiterfassung.setBeschreibung(r.getString("beschreibung"));
                zeiterfassung.setDatum(r.getDate("datum").getTime());

                result.add(zeiterfassung);
            }
        } catch (Exception a) {
        } finally {
            if (r != null) {
                try {
                    r.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * Buchungstyp von der Id des buchungstyp aus der db holen.
     * @param buchungId buchungsId
     * @return Buchungsname
     */
    private static String getBuchungNameFromId(int buchungId) {
        ResultSet r;
        String result = null;
        r = Datenbank.executeQuery("SELECT name FROM buchung WHERE id =" + buchungId);
        try {
            result = r.getString("name");
        } catch (Exception a) {
        } finally {
            if (r != null) {
                try {
                    r.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * Umwandlung von java.util.Date zu java.sql.Date für erfassungen auf der DB.
     * @param uDate date
     * @return sql date
     */
    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;

    }

    /**
     * Eine Zeiterfassung auf der DB löschen
     * @param id zeiterfassungId
     */
    public static void zeiterfassungLoeschen(int id) {

        String sql = "DELETE FROM zeiterfassung WHERE id= " + id + "; ";
        try (Connection conn = Datenbank.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}

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

public class ZeiterfassungDAO {

    public static void updateZeiterfassung(Date date, int user, double zeit, int buchungId) {
        String sql = "INSERT INTO Zeiterfassung (persid,zeit,datum,buchungId) values(" +
                user + "," + zeit + "," + date.getTime() + "," + buchungId + ");";

        try (Connection conn = Datenbank.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static double getZeiterfassung(Date date, int user) {
        ResultSet rs = null;
        double result = 0;
        String sql = "SELECT * from zeiterfassung where datum=" + date.getTime() + " AND persId=" + user;
        Connection conn = Datenbank.getConnection();


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

    public static void insertZeiterfassung(Date date, int user, double zeit) {
        String sql = "INSERT INTO Zeiterfassung (persId,zeit,datum) VALUES(?,?,?)";

        // Connection conn = Datenbank.getConnection();
        try (Connection conn = Datenbank.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, user);
            pstmt.setDouble(2, zeit);
            pstmt.setDate(3, convertUtilToSql(date));

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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

    public static ObservableList<Zeiterfassung> getErfassungen(Date date, int user) {
        ResultSet r = null;
        ObservableList<Zeiterfassung> result = FXCollections.observableArrayList();
        try {
            r = Datenbank.executeQuery("SELECT * from zeiterfassung where datum=" + convertUtilToSql(date).getTime() + " AND persId=" + user);
            while (r.next()) {
                Zeiterfassung zeiterfassung = new Zeiterfassung();
                Buchung buchung = new Buchung();

                zeiterfassung.setId(r.getInt("id"));
                zeiterfassung.setZeit(r.getDouble("zeit"));

                buchung.setId(r.getInt("buchungId"));
                buchung.setBuchungName(getBuchungNameFromId(r.getInt("buchungId")));

                zeiterfassung.setBuchung(buchung);
                zeiterfassung.setDatum(1);

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

    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;

    }

    public static void zeiterfassungLoeschen(int id) {

        String sql = "DELETE FROM zeiterfassung WHERE id= " + id + "; ";
        try (Connection conn = Datenbank.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}

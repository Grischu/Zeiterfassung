package Database;

import com.sun.xml.internal.bind.v2.model.core.ID;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ZeiterfassungDAO {

    public static void updateZeiterfassung(Date date, int user, int zeit) {
        String sql = "UPDATE Zeiterfassung SET zeit = ? WHERE  persId = ? AND datum = ?";

        // Connection conn = Datenbank.getConnection();
        try (Connection conn = Datenbank.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, zeit);
            pstmt.setInt(2, user);
            pstmt.setDate(3, convertUtilToSql(date));


            // update
            int id = pstmt.executeUpdate();
            if(id == 0) {
                insertZeiterfassung(date, user, zeit);
            }
            System.out.println(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getZeiterfassung(Date date, int user) {
        ResultSet rs = null;
        int result = 0;
        String sql = "SELECT zeit from zeiterfassung where datum=? AND persId=?";
        Connection conn = Datenbank.getConnection();


        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, convertUtilToSql(date));
            pstmt.setInt(2, user);

            rs = pstmt.executeQuery();
            if(!rs.isClosed()) result = rs.getInt("zeit");

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

    public static void insertZeiterfassung(Date date, int user, int zeit) {
        String sql = "INSERT INTO Zeiterfassung (persId,zeit,datum) VALUES(?,?,?)";

        // Connection conn = Datenbank.getConnection();
        try (Connection conn = Datenbank.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, user);
            pstmt.setInt(2, zeit);
            pstmt.setDate(3, convertUtilToSql(date));

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;

    }

}

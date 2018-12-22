package Database;

import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StartfensterDAO {

	public static ObservableList<String> getKlasse() {
		ResultSet rs = null;
		ObservableList<String> result = FXCollections.observableArrayList();
		try {

			rs = Datenbank.executeQuery("SELECT name FROM klasse");
			while (rs.next()) {

				result.add(rs.getString("name"));
			}
		} catch (Exception a) {
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
	
	public static int getKlasseFromName(String name) {
		ResultSet rs = null;
		int result = 0;
		try {

			rs = Datenbank.executeQuery("SELECT id FROM klasse where name='" + name + "'");

			result = rs.getInt("id");

		} catch (Exception a) {
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

	public static int getIdFromName(String name) {
		ResultSet rs = null;
		int result = 0;
		try {

			rs = Datenbank.executeQuery("SELECT id FROM klasse where name='" + name + "'");

			result = rs.getInt("id");

		} catch (Exception a) {
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

}

package Database;


import java.sql.ResultSet;

public class LoginDAO {

    public static int login(String userName, String userPassword) {

        ResultSet r;
        int result = 0;
        r = Datenbank.executeQuery("SELECT id FROM user WHERE userName ='" + userName + "' and userPassword='" + userPassword + "'");
        try {
            result = r.getInt("id");
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
}

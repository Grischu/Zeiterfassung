package Application;

/**
 * Klasse für das handling des Users welcher eingeloggt ist.
 */
public class LoginApp {
    static int user;

    public static int getUser() {
        return user;
    }

    public static void setUser(int user) {
        LoginApp.user = user;
    }
}

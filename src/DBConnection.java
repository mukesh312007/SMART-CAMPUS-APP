import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/smart_campus";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println(" Database connected!");
        } catch (Exception e) {
            System.out.println(" Connection failed: " + e.getMessage());
        }
        return conn;
    }
}

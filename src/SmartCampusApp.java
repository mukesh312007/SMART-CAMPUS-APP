import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class SmartCampusApp extends JFrame {
    public SmartCampusApp() {
        setTitle("Smart Campus System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database not connected!");
            System.exit(0);
        }

        JTabbedPane tabs = new JTabbedPane();
		tabs.add("Navigation", new NavigationModule());
        tabs.add("Events", new EventModule(conn));
        tabs.add("Cafeteria", new CafeteriaModule(conn));

        add(tabs, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SmartCampusApp());
    }
}

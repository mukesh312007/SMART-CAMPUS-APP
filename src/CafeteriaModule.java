import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CafeteriaModule extends JPanel {
    Connection conn;
    JComboBox<String> dayBox;
    JTextArea resultArea;

    public CafeteriaModule(Connection connection) {
        this.conn = connection;
        setLayout(new BorderLayout());

        dayBox = new JComboBox<>(new String[]{
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
        });
        JButton showBtn = new JButton("Show Menu");
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        JPanel top = new JPanel();
        top.add(dayBox);
        top.add(showBtn);

        showBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadMenu();
            }
        });

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void loadMenu() {
        String day = (String) dayBox.getSelectedItem();
        resultArea.setText("");
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM cafeteria_menu WHERE day=?")) {
            ps.setString(1, day);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                resultArea.append("Menu for " + day + ":\n" + rs.getString("menu_items"));
            } else {
                resultArea.append("No menu found for " + day);
            }
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
}

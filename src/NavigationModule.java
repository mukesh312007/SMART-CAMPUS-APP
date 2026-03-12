import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NavigationModule extends JPanel {

    private JTextArea infoArea;

    public NavigationModule() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 245, 255));

        JLabel heading = new JLabel(" Smart Campus Map");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        // Center "Map" panel
        JPanel mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(240, 250, 255));

                // Draw simple map grid and connecting lines
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(60, 60, 500, 350);

                g.setColor(Color.GRAY);
                g.drawLine(180, 150, 400, 150);
                g.drawLine(180, 150, 180, 300);
                g.drawLine(400, 150, 400, 300);

                // Draw location circles
                g.setColor(new Color(100, 180, 255));
                g.fillOval(150, 120, 60, 60); // Library
                g.fillOval(370, 120, 60, 60); // Labs
                g.fillOval(150, 270, 60, 60); // Cafeteria
                g.fillOval(370, 270, 60, 60); // Admin Block
                g.fillOval(260, 200, 60, 60); // Parking

                // Labels
                g.setColor(Color.BLACK);
                g.setFont(new Font("Segoe UI", Font.BOLD, 14));
                g.drawString("Library", 155, 115);
                g.drawString("Labs", 385, 115);
                g.drawString("Cafeteria", 145, 350);
                g.drawString("Admin", 385, 350);
                g.drawString("Parking", 270, 195);
            }
        };

        mapPanel.setPreferredSize(new Dimension(600, 500));
        mapPanel.setLayout(null);

        // Transparent buttons on map
        addMapButton(mapPanel, "Library", 160, 130);
        addMapButton(mapPanel, "Labs", 380, 130);
        addMapButton(mapPanel, "Cafeteria", 160, 280);
        addMapButton(mapPanel, "Admin Block", 380, 280);
        addMapButton(mapPanel, "Parking", 270, 210);

        add(mapPanel, BorderLayout.CENTER);

        // Info panel at bottom
        infoArea = new JTextArea(4, 30);
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setText("Select a location to view navigation details.");
        JScrollPane scroll = new JScrollPane(infoArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Location Info"));
        add(scroll, BorderLayout.SOUTH);
    }

    private void addMapButton(JPanel mapPanel, String name, int x, int y) {
        JButton btn = new JButton();
        btn.setBounds(x, y, 60, 60);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> showInfo(name));

        mapPanel.add(btn);
    }

    private void showInfo(String location) {
        switch (location) {
            case "Library":
                infoArea.setText(" Library: Located near the north block. Contains 20,000+ books and a study lounge.\nRoute: From main gate -> straight -> 1st left -> Library.");
                break;
            case "Labs":
                infoArea.setText(" Labs: Computer Science, Electronics, and Physics labs are here.\nRoute: From Library -> east side road -> Labs building.");
                break;
            case "Cafeteria":
                infoArea.setText(" Cafeteria: Serves breakfast, lunch, snacks, and beverages.\nRoute: From main gate -> straight -> south corner -> Cafeteria.");
                break;
            case "Admin Block":
                infoArea.setText(" Admin Block: Admission, Finance, and Office sections.\nRoute: From main gate -> right road -> Admin block.");
                break;
            case "Parking":
                infoArea.setText(" Parking Area: For staff and students, capacity 150+ vehicles.\nRoute: From main gate -> left ->` Parking zone.");
                break;
        }
    }
}

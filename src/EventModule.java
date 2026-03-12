import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.File;

public class EventModule extends JPanel {
    Connection conn;
    JPanel eventPanel;  // main container

    public EventModule(Connection connection) {
        this.conn = connection;
        setLayout(new BorderLayout());

        JButton refreshBtn = new JButton("Show Events");
        eventPanel = new JPanel();
        eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(eventPanel);

        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadEvents();
            }
        });

        add(refreshBtn, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadEvents() {
        eventPanel.removeAll();
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM events ORDER BY event_date");
            while (rs.next()) {
                String title = rs.getString("title");
                Date date = rs.getDate("event_date");
                String desc = rs.getString("description");
                String imagePath = rs.getString("image_path");

                JPanel card = createEventCard(title, date, desc, imagePath);
                eventPanel.add(card);
                eventPanel.add(Box.createVerticalStrut(15)); // space between cards
            }
        } catch (Exception ex) {
            JLabel err = new JLabel("Error loading events: " + ex.getMessage());
            eventPanel.add(err);
        }
        eventPanel.revalidate();
        eventPanel.repaint();
    }

    private JPanel createEventCard(String title, Date date, String desc, String imagePath) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(500, 250));

        JLabel titleLbl = new JLabel("~ " + title + " (" + date + ")");
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JTextArea descArea = new JTextArea(desc);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setEditable(false);
        descArea.setBackground(new Color(245, 245, 245));

        // Image section
        JLabel imageLabel = new JLabel();
        if (imagePath != null && !imagePath.isEmpty()) {
            File imgFile = new File("../" + imagePath);
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
                Image scaledImg = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                imageLabel.setText(" Image not found");
            }
        } else {
            imageLabel.setText("No image for this event");
        }

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(titleLbl, BorderLayout.NORTH);
        textPanel.add(new JScrollPane(descArea), BorderLayout.CENTER);

        card.add(textPanel, BorderLayout.CENTER);
        card.add(imageLabel, BorderLayout.EAST);
        return card;
    }
}

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewComplaints {

    static JPanel mainPanel;

    public static JPanel createPanel(CardLayout cardLayout, JPanel parentPanel) {

        JPanel panel = new JPanel(new BorderLayout());

        // TITLE
        JLabel title = new JLabel("My Complaints");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        panel.add(title, BorderLayout.NORTH);

        // CARD CONTAINER
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2, 20, 20));
        mainPanel.setBackground(new Color(240, 244, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);

        panel.add(scrollPane, BorderLayout.CENTER);

        // BACK BUTTON
        JButton backBtn = new JButton("← Back");
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(58, 123, 213));
        backBtn.setForeground(Color.WHITE);

        backBtn.addActionListener(e -> cardLayout.show(parentPanel, "HOME"));

        JPanel bottom = new JPanel();
        bottom.add(backBtn);

        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // CARD UI
    private static JPanel createCard(String issue, String location, String status, String priority, String resolved) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        
        private static void showDetails(String issue, String location, String status, String priority, String resolved) {

            JDialog dialog = new JDialog();
            dialog.setTitle("Complaint Details");
            dialog.setSize(350, 300);
            dialog.setLocationRelativeTo(null);
            dialog.setLayout(new BorderLayout());

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JLabel issueLabel = new JLabel("Issue: " + issue);
            JLabel locationLabel = new JLabel("Location: " + location);
            JLabel statusLabel = new JLabel("Status: " + status);
            JLabel priorityLabel = new JLabel("Priority: " + priority);
            JLabel resolvedLabel = new JLabel("Resolved By: " + resolved);

            issueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

            panel.add(issueLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(locationLabel);
            panel.add(statusLabel);
            panel.add(priorityLabel);
            panel.add(resolvedLabel);

            JButton closeBtn = new JButton("Close");
            closeBtn.addActionListener(e -> dialog.dispose());

            JPanel bottom = new JPanel();
            bottom.add(closeBtn);

            dialog.add(panel, BorderLayout.CENTER);
            dialog.add(bottom, BorderLayout.SOUTH);

            dialog.setVisible(true);
        }

        // ISSUE TITLE
        JLabel issueLabel = new JLabel(issue);
        issueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // LOCATION
        JLabel locationLabel = new JLabel("📍 " + location);

        // STATUS BADGE
        JLabel statusLabel = new JLabel(status);
        statusLabel.setOpaque(true);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        if (status.equalsIgnoreCase("Pending")) {
            statusLabel.setBackground(new Color(255, 193, 7));
        } else if (status.equalsIgnoreCase("In Progress")) {
            statusLabel.setBackground(new Color(33, 150, 243));
        } else if (status.equalsIgnoreCase("Resolved")) {
            statusLabel.setBackground(new Color(76, 175, 80));
        } else {
            statusLabel.setBackground(Color.GRAY);
        }

        // EXTRA INFO
        JLabel priorityLabel = new JLabel("Priority: " + priority);
        JLabel resolvedLabel = new JLabel("Resolved By: " + resolved);

        // BUTTON
        JButton viewBtn = new JButton("View Details");
        viewBtn.setFocusPainted(false);
        viewBtn.setBackground(new Color(0, 120, 215));
        viewBtn.setForeground(Color.WHITE);
        
        viewBtn.addActionListener(e -> 
        showDetails(issue, location, status, priority, resolved)
    );

        // ADD COMPONENTS
        card.add(issueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(locationLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(statusLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(priorityLabel);
        card.add(resolvedLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(viewBtn);

        return card;
    }

    //  LOAD DATA FROM DB
    public static void loadData(String userName) {

        if (mainPanel == null) return;

        mainPanel.removeAll();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM complaints WHERE name LIKE ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%" + userName.trim() + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String issue = rs.getString("issue");
                String location = rs.getString("location");
                String status = rs.getString("status");

                String priority = rs.getString("priority");
                if (priority == null) priority = "Low";

                String resolved = rs.getString("resolved_by");
                if (resolved == null) resolved = "-";

                JPanel card = createCard(issue, location, status, priority, resolved);
                mainPanel.add(card);
            }

            mainPanel.revalidate();
            mainPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
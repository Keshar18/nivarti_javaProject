import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewComplaints {

    static JPanel mainPanel;

    public static JPanel createPanel(CardLayout cardLayout, JPanel parentPanel) {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("My Complaints");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        panel.add(title, BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2, 20, 20));
        mainPanel.setBackground(new Color(240, 244, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backBtn = new JButton("← Back");
        backBtn.setBackground(new Color(58, 123, 213));
        backBtn.setForeground(Color.WHITE);

        backBtn.addActionListener(e -> cardLayout.show(parentPanel, "HOME"));

        JPanel bottom = new JPanel();
        bottom.add(backBtn);

        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // 🔥 CARD UI
    private static JPanel createCard(int id, String issue, String location, String status,
                                     String priority, String resolved,
                                     String createdDate, String resolvedDate) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel issueLabel = new JLabel(issue);
        issueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel locationLabel = new JLabel("📍 " + location);

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

        JLabel priorityLabel = new JLabel("Priority: " + priority);
        JLabel resolvedLabel = new JLabel("Resolved By: " + resolved);

        JLabel createdLabel = new JLabel("📅 Created: " + createdDate);
        JLabel resolvedDateLabel = new JLabel("✅ Resolved On: " + resolvedDate);

        JButton viewBtn = new JButton("View Details");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        viewBtn.setBackground(new Color(0, 120, 215));
        viewBtn.setForeground(Color.WHITE);

        editBtn.setBackground(new Color(255, 152, 0));
        editBtn.setForeground(Color.WHITE);

        deleteBtn.setBackground(new Color(244, 67, 54));
        deleteBtn.setForeground(Color.WHITE);

        // 🔥 VIEW
        viewBtn.addActionListener(e ->
                showDetails(issue, location, status, priority, resolved, createdDate, resolvedDate)
        );

        // 🔥 EDIT
        editBtn.addActionListener(e -> {

            String newIssue = JOptionPane.showInputDialog("Edit Issue:", issue);
            String newLocation = JOptionPane.showInputDialog("Edit Location:", location);

            if (newIssue != null && newLocation != null) {
                try {
                    Connection con = DBConnection.getConnection();

                    String query = "UPDATE complaints SET issue=?, location=? WHERE id=?";
                    PreparedStatement ps = con.prepareStatement(query);

                    ps.setString(1, newIssue);
                    ps.setString(2, newLocation);
                    ps.setInt(3, id);

                    ps.executeUpdate();

                    loadData(MainGUI.currentUser);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 🔥 DELETE
        deleteBtn.addActionListener(e -> {

            int confirm = JOptionPane.showConfirmDialog(null,
                    "Delete this complaint?", "Confirm", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Connection con = DBConnection.getConnection();

                    String query = "DELETE FROM complaints WHERE id=?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1, id);

                    ps.executeUpdate();

                    loadData(MainGUI.currentUser);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);

        card.add(issueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(locationLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(statusLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(priorityLabel);
        card.add(resolvedLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(createdLabel);
        card.add(resolvedDateLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(viewBtn);
        card.add(btnPanel);

        return card;
    }

    // 🔥 POPUP
    private static void showDetails(String issue, String location, String status,
                                    String priority, String resolved,
                                    String createdDate, String resolvedDate) {

        JDialog dialog = new JDialog();
        dialog.setTitle("Complaint Details");
        dialog.setSize(350, 320);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Issue: " + issue));
        panel.add(new JLabel("Location: " + location));
        panel.add(new JLabel("Status: " + status));
        panel.add(new JLabel("Priority: " + priority));
        panel.add(new JLabel("Resolved By: " + resolved));
        panel.add(new JLabel("Created: " + createdDate));
        panel.add(new JLabel("Resolved On: " + resolvedDate));

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dialog.dispose());

        JPanel bottom = new JPanel();
        bottom.add(closeBtn);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(bottom, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // 🔥 LOAD DATA
    public static void loadData(String userName) {

        if (mainPanel == null) return;

        mainPanel.removeAll();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM complaints WHERE LOWER(name) LIKE ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%" + userName.trim().toLowerCase() + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");

                String issue = rs.getString("issue");
                String location = rs.getString("location");
                String status = rs.getString("status");

                String priority = rs.getString("priority");
                if (priority == null) priority = "Low";

                String resolved = rs.getString("resolved_by");
                if (resolved == null) resolved = "-";

                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp resolvedAt = rs.getTimestamp("resolved_at");

                String createdDate = (createdAt != null) ? createdAt.toString() : "-";
                String resolvedDate = (resolvedAt != null) ? resolvedAt.toString() : "-";

                JPanel card = createCard(id, issue, location, status,
                        priority, resolved, createdDate, resolvedDate);

                mainPanel.add(card);
            }

            mainPanel.revalidate();
            mainPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
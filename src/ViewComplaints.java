import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewComplaints {

    static JPanel mainPanel;
    static JTextField searchField;
    static String currentFilter = "All";

    public static JPanel createPanel(CardLayout cardLayout, JPanel parentPanel) {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("My Complaints");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(title, BorderLayout.NORTH);

        // TOP PANEL
        JPanel topPanel = new JPanel();

        searchField = new JTextField(15);

        JButton allBtn = new JButton("All");
        JButton pendingBtn = new JButton("Pending");
        JButton progressBtn = new JButton("In Progress");
        JButton resolvedBtn = new JButton("Resolved");

        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(allBtn);
        topPanel.add(pendingBtn);
        topPanel.add(progressBtn);
        topPanel.add(resolvedBtn);

        panel.add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

        // MAIN PANEL (FIXED)
        mainPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        mainPanel.setBackground(new Color(240, 244, 250));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.CENTER);

        //  BACK
        JButton backBtn = new JButton("← Back");
        backBtn.addActionListener(e -> cardLayout.show(parentPanel, "HOME"));
        panel.add(backBtn, BorderLayout.SOUTH);

        //  ACTIONS
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                loadData(MainGUI.currentUser);
            }
        });

        allBtn.addActionListener(e -> { currentFilter = "All"; loadData(MainGUI.currentUser); });
        pendingBtn.addActionListener(e -> { currentFilter = "Pending"; loadData(MainGUI.currentUser); });
        progressBtn.addActionListener(e -> { currentFilter = "In Progress"; loadData(MainGUI.currentUser); });
        resolvedBtn.addActionListener(e -> { currentFilter = "Resolved"; loadData(MainGUI.currentUser); });

        return panel;
    }

    // CARD
    private static JPanel createCard(int id, String issue, String location, String status,
                                     String priority, String resolved,
                                     String createdDate, String resolvedDate) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        //  IMPORTANT SIZE FIX
        card.setPreferredSize(new Dimension(280, 190));

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

        if (status.equalsIgnoreCase("Pending"))
            statusLabel.setBackground(new Color(255, 193, 7));
        else if (status.equalsIgnoreCase("In Progress"))
            statusLabel.setBackground(new Color(33, 150, 243));
        else
            statusLabel.setBackground(new Color(76, 175, 80));

        JLabel priorityLabel = new JLabel("Priority: " + priority);
        JLabel resolvedLabel = new JLabel("Resolved By: " + resolved);

        JLabel createdLabel = new JLabel("📅 Created: " + createdDate);
        JLabel resolvedDateLabel = new JLabel("✅ Resolved On: " + resolvedDate);

        JButton viewBtn = new JButton("View Details");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        //  BUTTON STYLE
        Dimension btnSize = new Dimension(100, 30);

        viewBtn.setPreferredSize(btnSize);
        editBtn.setPreferredSize(btnSize);
        deleteBtn.setPreferredSize(btnSize);

        viewBtn.setBackground(new Color(33,150,243));
        editBtn.setBackground(new Color(255,152,0));
        deleteBtn.setBackground(new Color(244,67,54));

        viewBtn.setForeground(Color.WHITE);
        editBtn.setForeground(Color.WHITE);
        deleteBtn.setForeground(Color.WHITE);

        viewBtn.setFocusPainted(false);
        editBtn.setFocusPainted(false);
        deleteBtn.setFocusPainted(false);

        // VIEW
        viewBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(null,
                        "Issue: " + issue + "\nLocation: " + location));

        // EDIT
        editBtn.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE complaints SET issue=?, location=? WHERE id=?");

                ps.setString(1, issue);
                ps.setString(2, location);
                ps.setInt(3, id);

                ps.executeUpdate();
                loadData(MainGUI.currentUser);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // DELETE
        deleteBtn.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM complaints WHERE id=?");

                ps.setInt(1, id);
                ps.executeUpdate();

                loadData(MainGUI.currentUser);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnPanel.setBackground(Color.WHITE);

        btnPanel.add(viewBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);

        card.add(issueLabel);
        card.add(locationLabel);
        card.add(statusLabel);
        card.add(priorityLabel);
        card.add(resolvedLabel);
        card.add(createdLabel);
        card.add(resolvedDateLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(btnPanel);

        return card;
    }

    //  LOAD DATA
    public static void loadData(String userName) {

        if (mainPanel == null) return;

        mainPanel.removeAll();

        try {
            Connection con = DBConnection.getConnection();

            String search = searchField.getText().toLowerCase();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM complaints WHERE LOWER(name) LIKE ?");
            ps.setString(1, "%" + userName.toLowerCase() + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String issue = rs.getString("issue");
                String location = rs.getString("location");
                String status = rs.getString("status");

                if (!search.isEmpty() &&
                        !(issue.toLowerCase().contains(search) ||
                          location.toLowerCase().contains(search)))
                    continue;

                if (!currentFilter.equals("All") &&
                        !status.equalsIgnoreCase(currentFilter))
                    continue;

                mainPanel.add(createCard(
                        rs.getInt("id"),
                        issue,
                        location,
                        status,
                        rs.getString("priority"),
                        rs.getString("resolved_by"),
                        String.valueOf(rs.getTimestamp("created_at")),
                        String.valueOf(rs.getTimestamp("resolved_at"))
                ));
            }

            mainPanel.revalidate();
            mainPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
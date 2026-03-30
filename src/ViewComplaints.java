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

        // 🔍 TOP PANEL (SEARCH + FILTER)
        JPanel topPanel = new JPanel();

        searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");

        JButton allBtn = new JButton("All");
        JButton pendingBtn = new JButton("Pending");
        JButton progressBtn = new JButton("In Progress");
        JButton resolvedBtn = new JButton("Resolved");

        topPanel.add(new JLabel("🔍"));
        topPanel.add(searchField);
        topPanel.add(searchBtn);

        topPanel.add(allBtn);
        topPanel.add(pendingBtn);
        topPanel.add(progressBtn);
        topPanel.add(resolvedBtn);

        panel.add(topPanel, BorderLayout.SOUTH);

        // 🧾 MAIN PANEL
        mainPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        mainPanel.setBackground(new Color(240, 244, 250));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 🔙 BACK
        JButton backBtn = new JButton("← Back");
        backBtn.addActionListener(e -> cardLayout.show(parentPanel, "HOME"));
        panel.add(backBtn, BorderLayout.PAGE_END);

        // 🔥 ACTIONS
        searchBtn.addActionListener(e -> loadData(MainGUI.currentUser));

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

    private static JPanel createCard(String issue, String location, String status) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel issueLabel = new JLabel(issue);
        issueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel locationLabel = new JLabel("📍 " + location);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setOpaque(true);
        statusLabel.setForeground(Color.WHITE);

        if (status.equalsIgnoreCase("Pending"))
            statusLabel.setBackground(Color.ORANGE);
        else if (status.equalsIgnoreCase("Resolved"))
            statusLabel.setBackground(Color.GREEN);
        else
            statusLabel.setBackground(Color.BLUE);

        card.add(issueLabel);
        card.add(locationLabel);
        card.add(statusLabel);

        return card;
    }

    public static void loadData(String userName) {

        if (mainPanel == null) return;

        mainPanel.removeAll();

        try {
            Connection con = DBConnection.getConnection();

            String search = searchField.getText().trim().toLowerCase();

            String query = "SELECT * FROM complaints WHERE LOWER(name) LIKE ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%" + userName.toLowerCase() + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String issue = rs.getString("issue").toLowerCase();
                String location = rs.getString("location").toLowerCase();
                String status = rs.getString("status");

                // 🔍 SEARCH FILTER
                if (!search.isEmpty() &&
                        !(issue.contains(search) || location.contains(search))) {
                    continue;
                }

                // 🎯 STATUS FILTER
                if (!currentFilter.equals("All") &&
                        !status.equalsIgnoreCase(currentFilter)) {
                    continue;
                }

                mainPanel.add(createCard(issue, location, status));
            }

            mainPanel.revalidate();
            mainPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
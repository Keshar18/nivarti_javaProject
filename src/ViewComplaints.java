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

        // ✅ UPDATED MAIN PANEL (3 CARDS PER ROW)
        mainPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        mainPanel.setBackground(new Color(240, 244, 250));

        // ✅ SCROLL FIX (cards stretch na ho)
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(mainPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.CENTER);

        // BACK BUTTON
        JButton backBtn = new JButton("← Back");
        backBtn.addActionListener(e -> {
            cardLayout.show(parentPanel, "HOME");
            MainGUI.refreshDashboard();
        });
        panel.add(backBtn, BorderLayout.SOUTH);

        // ACTIONS
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                loadData(Session.userEmail);
            }
        });

        allBtn.addActionListener(e -> { currentFilter = "All"; loadData(Session.userEmail); });
        pendingBtn.addActionListener(e -> { currentFilter = "Pending"; loadData(Session.userEmail); });
        progressBtn.addActionListener(e -> { currentFilter = "In Progress"; loadData(Session.userEmail); });
        resolvedBtn.addActionListener(e -> { currentFilter = "Resolved"; loadData(Session.userEmail); });

        return panel;
    }

    // ✅ CARD UI UPDATED
    private static JPanel createCard(int id, String issue, String location, String status,
            String priority, String resolved,
            String createdDate, String resolvedDate,
            String category)  {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        // ✅ SIZE + PADDING FIX
        card.setPreferredSize(new Dimension(300, 200));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // ✅ BIGGER FONT
        JLabel issueLabel = new JLabel(issue);
        issueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        issueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel locationLabel = new JLabel("📍 " + location);
        locationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        locationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setOpaque(true);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (status.equalsIgnoreCase("Pending"))
            statusLabel.setBackground(new Color(255, 193, 7));
        else if (status.equalsIgnoreCase("In Progress"))
            statusLabel.setBackground(new Color(33, 150, 243));
        else
            statusLabel.setBackground(new Color(76, 175, 80));

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        deleteBtn.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM complaints WHERE id=?");

                ps.setInt(1, id);
                ps.executeUpdate();

                loadData(Session.userEmail);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JLabel categoryLabel = new JLabel("Category: " + category);
        categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ADD COMPONENTS
        card.add(issueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(locationLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(statusLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(deleteBtn);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(categoryLabel);

        return card;
    }

    // LOAD DATA
    public static void loadData(String userName) {

        if (mainPanel == null || userName == null) return;

        mainPanel.removeAll();

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM complaints WHERE LOWER(name) LIKE ?");
            ps.setString(1, "%" + userName.toLowerCase() + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String issue = rs.getString("issue");
                String category = rs.getString("category");
                String location = rs.getString("location");
                String status = rs.getString("status");

                mainPanel.add(createCard(
                        rs.getInt("id"),
                        issue,
                        location,
                        status,
                        rs.getString("priority"),
                        rs.getString("resolved_by"),
                        String.valueOf(rs.getTimestamp("created_at")),
                        String.valueOf(rs.getTimestamp("resolved_at")),
                        category
                ));
            }

            if (mainPanel.getComponentCount() == 0) {
                JLabel empty = new JLabel("No complaints found 😌");
                empty.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                empty.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(empty);
            }

            mainPanel.revalidate();
            mainPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
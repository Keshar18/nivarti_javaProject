import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AuthorityPanel extends JFrame {

    JPanel panel;

    public AuthorityPanel() {

        System.out.println("🚀 Authority Panel opened");

        setTitle("Authority Panel");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== TOP FILTER BAR =====
        JTextField searchField = new JTextField(15);

        JButton allBtn = new JButton("All");
        JButton pendingBtn = new JButton("Pending");
        JButton inProgressBtn = new JButton("In Progress");
        JButton resolvedBtn = new JButton("Resolved");
        
        allBtn.addActionListener(e -> loadData("All"));
        pendingBtn.addActionListener(e -> loadData("Pending"));
        progressBtn.addActionListener(e -> loadData("In Progress"));
        resolvedBtn.addActionListener(e -> loadData("Resolved"));

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(allBtn);
        topPanel.add(pendingBtn);
        topPanel.add(inProgressBtn);
        topPanel.add(resolvedBtn);

        add(topPanel, BorderLayout.NORTH);

        // ===== CARD PANEL =====
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3, 10, 10));

        add(new JScrollPane(panel), BorderLayout.CENTER);

        // ===== BUTTON ACTIONS =====
        allBtn.addActionListener(e -> loadData("All"));
        pendingBtn.addActionListener(e -> loadData("Pending"));
        inProgressBtn.addActionListener(e -> loadData("In Progress"));
        resolvedBtn.addActionListener(e -> loadData("Resolved"));

        // ===== INITIAL LOAD =====
        loadData("All");

        setVisible(true);
    }

    // ===== LOAD DATA WITH FILTER =====
    void loadData(String statusFilter) {
        try {
            Connection con = DBConnection.getConnection();

            String query;
            if (statusFilter.equals("All")) {
                query = "SELECT * FROM complaints";
            } else {
                query = "SELECT * FROM complaints WHERE status=?";
            }

            PreparedStatement ps = con.prepareStatement(query);

            if (!statusFilter.equals("All")) {
                ps.setString(1, statusFilter + "%"  );
            }

            ResultSet rs = ps.executeQuery();

            panel.removeAll();

            while (rs.next()) {
                int id = rs.getInt("id");
                String issue = rs.getString("issue");
                String location = rs.getString("location");
                String status = rs.getString("status");
                String category = rs.getString("category");
                
                String resolvedBy = rs.getString("resolved_by");

                panel.add(createCard(id, issue, location, status, category, resolvedBy));
            }

            panel.revalidate();
            panel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== CARD UI =====
    JPanel createCard(int id, String issue, String location, String status, String category, String resolvedBy) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setBackground(Color.WHITE);

        JLabel issueLabel = new JLabel("<html><b>" + issue + "</b></html>");
        JLabel locationLabel = new JLabel("📍 " + location);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setOpaque(true);

        
        
        if (status.equalsIgnoreCase("Pending")) {
            statusLabel.setBackground(Color.ORANGE);
        } else if (status.equalsIgnoreCase("Resolved")) {
            statusLabel.setBackground(Color.GREEN);
        } else {
            statusLabel.setBackground(Color.CYAN);
        }

        JLabel categoryLabel = new JLabel("Category: " + category);
        
        JLabel resolvedLabel = new JLabel(
        	    resolvedBy == null ? "Not assigned" : "Resolved by: " + resolvedBy
        	);

        	

        JButton resolveBtn = new JButton("Resolve");

        resolveBtn.addActionListener(e -> updateStatus(id));

        card.add(issueLabel);
        card.add(locationLabel);
        card.add(statusLabel);
        
        card.add(categoryLabel);
        card.add(resolvedLabel);
        card.add(resolveBtn);

        return card;
    }

    // ===== UPDATE STATUS =====
    void updateStatus(int id) {
        try {
            String name = JOptionPane.showInputDialog(this, "Enter your name:");

            if (name == null || name.isEmpty()) return;

            Connection con = DBConnection.getConnection();

            String query = "UPDATE complaints SET status=?, resolved_by=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, "Resolved");
            ps.setString(2, name);
            ps.setInt(3, id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Marked as Resolved ✅");

            loadData("All");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewComplaints {

    public static JPanel createPanel(CardLayout cardLayout, JPanel mainPanel) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(244, 247, 251));

        // ===== TITLE =====
        JLabel title = new JLabel("My Complaints");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        panel.add(title, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] columns = {"Issue", "Location", "Status"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        panel.add(scrollPane, BorderLayout.CENTER);

        // ===== GET USER NAME =====
        String userName = JOptionPane.showInputDialog("Enter your name:");

        // ===== LOAD DATA =====
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT issue, location, status FROM complaints WHERE name = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String issue = rs.getString("issue");
                String location = rs.getString("location");
                String status = rs.getString("status");

                model.addRow(new Object[]{issue, location, status});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ===== BACK BUTTON =====
        JButton backBtn = new JButton("← Back");
        backBtn.setBackground(new Color(58, 123, 213));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(244, 247, 251));
        bottom.add(backBtn);

        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }
}
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewComplaints {

    static DefaultTableModel model;

    public static JPanel createPanel(CardLayout cardLayout, JPanel mainPanel) {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("My Complaints");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(title, BorderLayout.NORTH);

        String[] columns = {"Issue", "Location", "Status", "Priority", "Resolved By"};
        model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        panel.add(backBtn, BorderLayout.SOUTH);

        return panel;
    }

    public static void loadData(String userName) {

        if(model == null) return;

        model.setRowCount(0);

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

                // 🔥 SAFE NULL HANDLING
                String priority = rs.getString("priority");
                if(priority == null) priority = "Low";

                String resolved = rs.getString("resolved_by");
                if(resolved == null) resolved = "-";

                model.addRow(new Object[]{
                    issue,
                    location,
                    status,
                    priority,
                    resolved
                });
            }

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 IMPORTANT (error dikhega)
        }
    }
    
}
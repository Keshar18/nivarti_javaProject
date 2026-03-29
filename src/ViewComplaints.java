import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableCellRenderer;

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
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(35);

        // Header styling
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(58, 123, 213));
        table.getTableHeader().setForeground(Color.WHITE);
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (value == null) return c;

                String status = value.toString();

                c.setForeground(Color.WHITE);

                if (status.equalsIgnoreCase("Pending")) {
                    c.setBackground(new Color(255, 193, 7));
                } else if (status.equalsIgnoreCase("In Progress")) {
                    c.setBackground(new Color(33, 150, 243));
                } else if (status.equalsIgnoreCase("Resolved")) {
                    c.setBackground(new Color(76, 175, 80));
                } else {
                    c.setBackground(Color.GRAY);
                }

                return c;
            }
        });

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
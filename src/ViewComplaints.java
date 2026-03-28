import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.*;

public class ViewComplaints {

    static DefaultTableModel model;

    public static JPanel createPanel(CardLayout cardLayout, JPanel mainPanel) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(244, 247, 251));

        // TITLE
        JLabel title = new JLabel("My Complaints");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        panel.add(title, BorderLayout.NORTH);

        // TABLE
        String[] columns = {"Issue", "Location", "Status","Resolved By"};
        model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);

        // 🔥 TABLE UI
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(58, 123, 213));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(35);
        
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (row % 2 == 0) {
                    c.setBackground(new Color(245, 247, 250));
                } else {
                    c.setBackground(Color.WHITE);
                }

                return c;
            }
        });


        // 🔥 STATUS COLOR RENDERER
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                String status = value.toString();

                c.setForeground(Color.WHITE);

                if (status.equalsIgnoreCase("Pending")) {
                    c.setBackground(new Color(255, 193, 7)); // Yellow
                } 
                else if (status.equalsIgnoreCase("In Progress")) {
                    c.setBackground(new Color(33, 150, 243)); // Blue
                } 
                else if (status.equalsIgnoreCase("Resolved")) {
                    c.setBackground(new Color(76, 175, 80)); // Green
                } 
                else {
                    c.setBackground(Color.GRAY);
                }

                ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);

                return c;
            }
            
        });

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // BACK BUTTON
        JButton backBtn = new JButton("← Back");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        JPanel bottom = new JPanel();
        bottom.add(backBtn);

        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // 🔥 LOAD DATA METHOD (VERY IMPORTANT)
    public static void loadData(String userName) {

        if (model == null) return;

        model.setRowCount(0);

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT issue, location, status, resolved_by FROM complaints WHERE name = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("issue"),
                        rs.getString("location"),
                        rs.getString("status"),
                        rs.getString("resolved_by")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
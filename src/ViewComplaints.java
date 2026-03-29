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

        model.setRowCount(0);

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM complaints WHERE name = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("issue"),
                        rs.getString("location"),
                        rs.getString("status"),
                        rs.getString("priority") == null ? "-" : rs.getString("priority"),
                        rs.getString("resolved_by") == null ? "-" : rs.getString("resolved_by"))
                });
            }m

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
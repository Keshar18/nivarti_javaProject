import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        String[] columns = {"Issue", "Location", "Status"};
        model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        table.setRowHeight(30);

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

    // 🔥 LOAD DATA METHOD
    public static void loadData(String userName) {
    	   if(model == null) return;

        model.setRowCount(0); // clear table

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT issue, location, status FROM complaints WHERE name = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("issue"),
                        rs.getString("location"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




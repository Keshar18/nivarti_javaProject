import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.*;

public class ViewComplaints extends JFrame {

    JTable table;
    DefaultTableModel model;
    String userName; 

    public ViewComplaints(String userName) {
    	this.userName = userName;

        setTitle("All Complaints");
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Table columns
        String[] columns = {"ID", "Name", "Issue", "Location", "Status"};

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout()); // important

        JLabel title = new JLabel("Your Complaints", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadData();

        setVisible(true);
    }

    public void loadData() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM complaints WHERE name = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userName);
            

            ResultSet rs = ps.executeQuery();

            

            model.setRowCount(0); // it will clear old data

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String issue = rs.getString("issue");
                String location = rs.getString("location");
                String status = rs.getString("status");

                model.addRow(new Object[]{id, name, issue, location, status});
            }
            
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
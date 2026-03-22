import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewComplaints extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ViewComplaints() {

        setTitle("All Complaints");
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Table columns
        String[] columns = {"ID", "Name", "Issue", "Location", "Status"};

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        loadData();

        setVisible(true);
    }

    public void loadData() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM complaints";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String issue = rs.getString("issue");
                String location = rs.getString("location");
                String status = rs.getString("status");

                model.addRow(new Object[]{id, name, issue, location, status});
            }

        } catch (Exception e) {
            e.printStackTrace();
            //lellele
        }
    }
}
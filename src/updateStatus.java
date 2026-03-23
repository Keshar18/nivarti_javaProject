import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class UpdateStatus extends JFrame {

    JTable table;
    DefaultTableModel model;

    public UpdateStatus() {
        setTitle("Update Complaint Status");
        setSize(700, 400);
        setLocationRelativeTo(null);

        String[] columns = {"ID", "Name", "Issue", "Location", "Status"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton updateBtn = new JButton("Update Status");
        add(updateBtn, BorderLayout.SOUTH);

        loadData();

        // Button click
        updateBtn.addActionListener(e -> updateStatus());

        setVisible(true);
    }

    // Load data from DB
    public void loadData() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM complaints";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            model.setRowCount(0); // ✅ ADD THIS

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
        }
    }

    // Update status logic
    public void updateStatus() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a complaint first!");
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);

        String[] options = {"Pending", "In Progress", "Resolved"};
        String newStatus = (String) JOptionPane.showInputDialog(
                this,
                "Select new status:",
                "Update Status",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (newStatus != null) {
            try {
                Connection con = DBConnection.getConnection();
                String query = "UPDATE complaints SET status=? WHERE id=?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, newStatus);
                ps.setInt(2, id);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Status Updated!");

                model.setRowCount(0); // clear table
                loadData(); // reload

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
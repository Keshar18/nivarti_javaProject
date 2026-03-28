import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class UpdateStatus extends JFrame {

    JTable table;
    DefaultTableModel model;

    public void updateStatus() {
    	
    	 setTitle("Update Complaint Status");
    	    setSize(700, 400);
    	    setLocationRelativeTo(null);
    	    

    	    // 🔥 STEP 1: FILTER BUTTON PANEL (YAHAN ADD KARNA HAI)
    	    JPanel topPanel = new JPanel();

    	    JButton allBtn = new JButton("All");
    	    JButton pendingBtn = new JButton("Pending");
    	    JButton progressBtn = new JButton("In Progress");
    	    JButton resolvedBtn = new JButton("Resolved");

    	    topPanel.add(allBtn);
    	    topPanel.add(pendingBtn);
    	    topPanel.add(progressBtn);
    	    topPanel.add(resolvedBtn);

    	    add(topPanel, BorderLayout.NORTH);  // ✅ VERY IMPORTANT

    	    String[] columns = {"ID", "Name", "Issue", "Location", "Status"};
    	    model = new DefaultTableModel(columns, 0);
    	    table = new JTable(model);

    	    JScrollPane scrollPane = new JScrollPane(table);
    	    add(scrollPane, BorderLayout.CENTER);

    	    JButton updateBtn = new JButton("Update Status");
    	    add(updateBtn, BorderLayout.SOUTH);

    	    loadData("All");

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

        // Admin name input
        String adminName = JOptionPane.showInputDialog("Enter your name:");

        if (newStatus != null && adminName != null) {
            try {
                Connection con = DBConnection.getConnection();

                String query = "UPDATE complaints SET status=?, resolved_by=? WHERE id=?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, newStatus);
                ps.setString(2, adminName);
                ps.setInt(3, id);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Status Updated!");

                model.setRowCount(0);
                loadData();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Load data from DB
    public void loadData() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM complaints";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            model.setRowCount(0); // ADD THIS

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

                model.setRowCount(0); // it will clear table
                loadData(); // reload the page 

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
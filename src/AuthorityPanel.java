import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AuthorityPanel extends JFrame {

    JTable table;
    DefaultTableModel model;

    public AuthorityPanel() {

        setTitle("Authority Panel");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // TABLE
        String[] columns = {"ID", "Name", "Issue", "Location", "Category", "Status"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // BUTTON
        JButton resolveBtn = new JButton("Mark as Resolved");
        add(resolveBtn, BorderLayout.SOUTH);

        // LOAD DATA
        loadData();

        // BUTTON ACTION
        resolveBtn.addActionListener(e -> updateStatus());

        setVisible(true);
    }

    // 🔹 LOAD ONLY PENDING COMPLAINTS
    void loadData() {
        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("DB NOT CONNECTED ❌");
                return;
            }

            String query = "SELECT * FROM complaints WHERE status='Pending'";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("issue"),
                        rs.getString("location"),
                        rs.getString("category"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 UPDATE STATUS TO "Resolved (Pending)"
    void updateStatus() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        String name = JOptionPane.showInputDialog("Enter your name:");

      
        if (name == null || name.isEmpty()) return;

        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE complaints SET status=?, resolved_by=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, "Resolved (Pending)");
            ps.setString(2, name);
            ps.setInt(3, id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Marked as Resolved (Pending) ✅");

            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
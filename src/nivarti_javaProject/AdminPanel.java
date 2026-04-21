import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminPanel extends JFrame {

    JTable table;
    DefaultTableModel model;

    public AdminPanel() {

        setTitle("Admin Approval Panel");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        model = new DefaultTableModel();
        table = new JTable(model);

        model.setColumnIdentifiers(new String[]{
                "ID", "Name", "Issue", "Location", "Status", "Resolved By", "Badge"
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        
        JTextArea leaderboard = new JTextArea(10, 20);
        leaderboard.setEditable(false);

        add(new JScrollPane(leaderboard), BorderLayout.EAST);

        JButton approveBtn = new JButton("Approve ✅");
        JButton rejectBtn = new JButton("Reject ❌");

        JPanel panel = new JPanel();
        panel.add(approveBtn);
        panel.add(rejectBtn);

        add(panel, BorderLayout.SOUTH);

        loadData();

        // APPROVE
        approveBtn.addActionListener(e -> updateStatus("Resolved"));

        // REJECT
        rejectBtn.addActionListener(e -> updateStatus("In Progress"));

        setVisible(true);
    }

    void loadData() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM complaints WHERE status='Resolved (Pending)'";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);

            while (rs.next()) {
            	  String name = rs.getString("resolved_by");   
            	    String badge = getBadge(name);  
            	    
                model.addRow(new Object[]{
                		  rs.getInt("id"),
                	        rs.getString("name"),
                	        rs.getString("issue"),
                	        rs.getString("location"),
                	        rs.getString("status"),
                	        name,
                	        badge 
                        
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateStatus(String newStatus) {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a complaint first");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE complaints SET status=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, newStatus);
            ps.setInt(2, id);

            ps.executeUpdate();

            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    String getBadge(String name) {

        int count = 0;

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT COUNT(*) FROM complaints WHERE resolved_by=? AND status='Resolved'";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (count <= 5) return "🥉 Bronze";
        else if (count <= 15) return "🥈 Silver";
        else return "🥇 Gold";
    }
}
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
                "ID", "Name", "Issue", "Location", "Category", "Status", "Priority", "Resolved By", "Badge"
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
        loadLeaderboard(leaderboard);

        // APPROVE
        approveBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a complaint first!");
                return;
            }

            int id = (int) model.getValueAt(row, 0);

            try {
                Connection con = DBConnection.getConnection();

                String query = "UPDATE complaints SET status='Resolved' WHERE id=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, id);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Approved Successfully ✅");

                loadData();
                loadLeaderboard(leaderboard);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // REJECT
        rejectBtn.addActionListener(e -> updateStatus("In Progress"));

        setVisible(true);
    }

    void loadData() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM complaints WHERE status='Pending' " +
                    "ORDER BY CASE " +
                    "WHEN priority='High' THEN 1 " +
                    "WHEN priority='Medium' THEN 2 " +
                    "WHEN priority='Low' THEN 3 " +
                    "ELSE 4 END";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);

            while (rs.next()) {

                String resolvedBy = rs.getString("resolved_by");
                String badge = getBadge(resolvedBy);

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("issue"),
                        rs.getString("location"),
                        rs.getString("category"),
                        rs.getString("status"),
                        rs.getString("priority"),
                        resolvedBy,
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

    // ✅ SINGLE BADGE METHOD
    String getBadge(String name) {

        if (name == null || name.isEmpty()) return "-";

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT COUNT(*) FROM complaints WHERE resolved_by=? AND status='Resolved'";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);

                if (count >= 10) return "🏆 Gold";
                else if (count >= 5) return "🥈 Silver";
                else return "🥉 Bronze";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "-";
    }

    // ✅ SINGLE LEADERBOARD METHOD
    void loadLeaderboard(JTextArea leaderboard) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT resolved_by, COUNT(*) as total " +
                    "FROM complaints WHERE status='Resolved' " +
                    "GROUP BY resolved_by ORDER BY total DESC LIMIT 5";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder("🏆 Leaderboard\n\n");

            int rank = 1;
            while (rs.next()) {
                sb.append(rank++)
                        .append(". ")
                        .append(rs.getString("resolved_by"))
                        .append(" - ")
                        .append(rs.getInt("total"))
                        .append(" issues\n");
            }

            leaderboard.setText(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
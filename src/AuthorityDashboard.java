import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AuthorityDashboard extends JFrame {

    String authorityName;
    JLabel nameLabel, totalLabel, badgeLabel;
    JTable table;
    DefaultTableModel model;

    public AuthorityDashboard(String name) {
        this.authorityName = name;

        setTitle("Authority Dashboard");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔹 TOP PANEL
        JPanel top = new JPanel(new GridLayout(1, 3));

        nameLabel = new JLabel("Name: " + authorityName);
        totalLabel = new JLabel("Resolved: 0");
        badgeLabel = new JLabel("Badge: -");

        top.add(nameLabel);
        top.add(totalLabel);
        top.add(badgeLabel);

        add(top, BorderLayout.NORTH);

        // 🔹 TABLE
        model = new DefaultTableModel(new String[]{
                "ID", "Issue", "Status"
        }, 0);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        add(scrollPane, BorderLayout.CENTER);
        JButton resolveBtn = new JButton("Resolve Complaint");
        add(resolveBtn, BorderLayout.SOUTH);

        loadData();
        resolveBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a complaint first!");
                return;
            }

            int id = (int) model.getValueAt(row, 0);

            try {
                Connection con = DBConnection.getConnection();

                String query = "UPDATE complaints SET status='Resolved (Pending)', resolved_by=? WHERE id=?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, authorityName);
                ps.setInt(2, id);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Marked as Resolved");

                loadData();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        setVisible(true);
    }

    // 🔹 LOAD DATA
    void loadData() {
        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("DB NOT CONNECTED ❌");
                return;
            }

            String query = "SELECT id, issue, status FROM complaints WHERE status='Pending'";
            PreparedStatement ps = con.prepareStatement(query);
           

            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);

            int count = 0;

            while (rs.next()) {
                String status = rs.getString("status");

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("issue"),
                        status
                });

                if (status.contains("Resolved")) {
                    count++;
                }
            }

            totalLabel.setText("Resolved: " + count);
            badgeLabel.setText("Badge: " + getBadge(count));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 BADGE LOGIC
    String getBadge(int count) {
        if (count >= 10) return "🏆 Gold";
        else if (count >= 5) return "🥈 Silver";
        else return "🥉 Bronze";
    }
}
import javax.swing.*;
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

        // TOP PANEL
        JPanel top = new JPanel(new GridLayout(1, 3));

        nameLabel = new JLabel("Name: " + authorityName);
        totalLabel = new JLabel("Resolved: 0");
        badgeLabel = new JLabel("Badge: -");

        top.add(nameLabel);
        top.add(totalLabel);
        top.add(badgeLabel);

        add(top, BorderLayout.NORTH);

        // TABLE
        model = new DefaultTableModel(new String[]{
                "ID", "Issue", "Status"
        }, 0);

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();

        setVisible(true);
    }
    void loadData() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT id, issue, status FROM complaints WHERE resolved_by=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, authorityName);

            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);

            int count = 0;

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("issue"),
                        rs.getString("status")
                });

                if (rs.getString("status").equals("Resolved")) {
                    count++;
                }
            }

            totalLabel.setText("Resolved: " + count);
            badgeLabel.setText("Badge: " + getBadge(count));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String getBadge(int count) {
        if (count >= 10) return "🏆 Gold";
        else if (count >= 5) return "🥈 Silver";
        else return "🥉 Bronze";
    }
}
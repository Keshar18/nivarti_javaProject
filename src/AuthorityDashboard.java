import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AuthorityDashboard extends JFrame {

    String authorityName;
    JLabel nameLabel, totalLabel, badgeLabel;
    JPanel cardPanel;

    public AuthorityDashboard(String name) {
        this.authorityName = name;

        setTitle("Authority Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔹 TOP PANEL
        JPanel top = new JPanel(new GridLayout(1, 3));
        top.setBackground(Color.WHITE);

        nameLabel = new JLabel("Name: " + authorityName);
        totalLabel = new JLabel("Resolved: 0");
        badgeLabel = new JLabel("Badge: -");

        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        badgeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        top.add(nameLabel);
        top.add(totalLabel);
        top.add(badgeLabel);

        add(top, BorderLayout.NORTH);

        // 🔹 CARD PANEL (GRID)
        cardPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        cardPanel.setBackground(new Color(235, 240, 245));

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(235, 240, 245));

        add(scrollPane, BorderLayout.CENTER);

        // 🔹 BACK BUTTON
        JButton backBtn = new JButton("⬅ Back to Home");
        backBtn.setBackground(new Color(33, 150, 243));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        backBtn.addActionListener(e -> {
            new MainGUI();
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(235, 240, 245));
        bottomPanel.add(backBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        loadData();

        setVisible(true);
    }

    // 🔹 LOAD DATA
    void loadData() {
        try {
            Connection con = DBConnection.getConnection();

            cardPanel.removeAll();

            String query = "SELECT * FROM complaints WHERE status='Pending'";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            int count = 0;

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String issue = rs.getString("issue");
                String location = rs.getString("location");

                // 🔹 CARD
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
                card.setPreferredSize(new Dimension(220, 220));

                // 🔹 TEXT
                JLabel nameL = new JLabel("👤 " + name);
                nameL.setFont(new Font("Segoe UI", Font.BOLD, 15));

                JLabel issueL = new JLabel("<html><b>📝 " + issue + "</b></html>");
                issueL.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                JLabel locL = new JLabel("📍 " + location);
                locL.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                locL.setForeground(new Color(120, 120, 120));

                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
                textPanel.setBackground(Color.WHITE);

                textPanel.add(nameL);
                textPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                textPanel.add(issueL);
                textPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                textPanel.add(locL);

                // 🔹 BUTTON
                JButton resolveBtn = new JButton("✔ Resolve");
                resolveBtn.setBackground(new Color(76, 175, 80));
                resolveBtn.setForeground(Color.WHITE);
                resolveBtn.setFocusPainted(false);
                resolveBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

                resolveBtn.addActionListener(e -> {
                    try {
                        Connection c = DBConnection.getConnection();

                        String q = "UPDATE complaints SET status='Resolved', resolved_by=? WHERE id=?";
                        PreparedStatement p = c.prepareStatement(q);

                        p.setString(1, authorityName);
                        p.setInt(2, id);

                        p.executeUpdate();

                        JOptionPane.showMessageDialog(this, "Resolved ✅");

                        loadData();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                card.add(textPanel, BorderLayout.CENTER);
                card.add(resolveBtn, BorderLayout.SOUTH);

                cardPanel.add(card);
            }

            totalLabel.setText("Resolved: " + count);
            badgeLabel.setText("Badge: " + getBadge(count));

            cardPanel.revalidate();
            cardPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 BADGE SYSTEM
    String getBadge(int count) {
        if (count >= 10) return "🏆 Gold";
        else if (count >= 5) return "🥈 Silver";
        else return "🥉 Bronze";
    }
}
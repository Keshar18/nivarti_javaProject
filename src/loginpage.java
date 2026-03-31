import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPage {

    public static JPanel createPanel(CardLayout cardLayout, JPanel mainPanel) {

        // 🔥 MAIN BACKGROUND (same theme)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 244, 250));

        // 🔥 CARD (center box)
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        card.setPreferredSize(new Dimension(350, 300));

        // 🔥 TITLE
        JLabel title = new JLabel("Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔥 FIELDS
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        Dimension fieldSize = new Dimension(300, 40);

        for (JTextField field : new JTextField[]{userField, passField}) {
            field.setMaximumSize(fieldSize);
            field.setBackground(new Color(245, 247, 250));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

        JLabel userLabel = new JLabel("Username");
        JLabel passLabel = new JLabel("Password");

        // 🔥 BUTTON
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(58, 123, 213)); // SAME BLUE
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(200, 40));

        JLabel msg = new JLabel("");
        msg.setForeground(Color.RED);
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔥 LOGIN ACTION
        loginBtn.addActionListener(e -> {

            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            try {
                Connection con = DBConnection.getConnection();

                String query = "SELECT * FROM users WHERE username=? AND password=?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    // 🔥 SAVE USER
                    MainGUI.currentUser = username;

                    // 🔥 GO HOME
                    cardLayout.show(mainPanel, "HOME");

                } else {
                    msg.setText("Invalid Username or Password ❌");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 🔥 ADD COMPONENTS
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        card.add(userLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(userField);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(passLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(passField);

        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(loginBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(msg);

        panel.add(card);

        return panel;
    }
}
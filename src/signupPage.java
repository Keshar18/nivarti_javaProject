import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SignupPage {

    public static JPanel createPanel(CardLayout cardLayout, JPanel mainPanel) {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 244, 250));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        card.setPreferredSize(new Dimension(350, 320));
        card.setMaximumSize(new Dimension(350, 320));

        JLabel title = new JLabel("Sign Up");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        Dimension fieldSize = new Dimension(300, 40);

        for (JTextField field : new JTextField[]{userField, passField}) {
            field.setMaximumSize(fieldSize);
            field.setBackground(new Color(245, 247, 250));
            field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        }

        JLabel userLabel = new JLabel("Username");
        JLabel passLabel = new JLabel("Password");

        // ALIGNMENT
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        userField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton signupBtn = new JButton("Create Account");
        signupBtn.setBackground(new Color(58, 123, 213));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFocusPainted(false);
        signupBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel msg = new JLabel("");
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        msg.setForeground(Color.RED);

        signupBtn.addActionListener(e -> {

            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                msg.setText("All fields required ❌");
                return;
            }

            try {
                Connection con = DBConnection.getConnection();

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO users(username, password) VALUES (?, ?)"
                );

                ps.setString(1, username);
                ps.setString(2, password);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Account Created ✅");

                // go to login
                cardLayout.show(mainPanel, "LOGIN");

            } catch (Exception ex) {
                msg.setText("Username already exists ❌");
            }
        });

        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        card.add(userLabel);
        card.add(userField);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(passLabel);
        card.add(passField);

        card.add(Box.createRigidArea(new Dimension(0, 20)));

        card.add(signupBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(msg);

        panel.add(card);

        return panel;
    }
}
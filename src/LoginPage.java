import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPage {

    public static JPanel createPanel(CardLayout cardLayout, JPanel mainPanel) {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 244, 250));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
        card.setPreferredSize(new Dimension(350, 380));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        int y = 0;

        // TITLE
        JLabel title = new JLabel("Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = y++;
        card.add(title, gbc);

        // EMAIL
        JLabel emailLabel = new JLabel("Email");
        gbc.gridy = y++;
        card.add(emailLabel, gbc);

        JTextField emailField = new JTextField();
        gbc.gridy = y++;
        card.add(emailField, gbc);

        // PASSWORD
        JLabel passLabel = new JLabel("Password");
        gbc.gridy = y++;
        card.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField();
        gbc.gridy = y++;
        card.add(passField, gbc);

        // LOGIN BUTTON
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(58, 123, 213));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        gbc.gridy = y++;
        card.add(loginBtn, gbc);

        // MESSAGE LABEL (FIXED POSITION)
        JLabel msg = new JLabel(" ");
        msg.setForeground(Color.RED);
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = y++;
        card.add(msg, gbc);

        // SIGNUP BUTTON
        JButton signupBtn = new JButton("Create Account");
        signupBtn.setBorderPainted(false);
        signupBtn.setContentAreaFilled(false);
        signupBtn.setForeground(new Color(58, 123, 213));
        gbc.gridy = y++;
        card.add(signupBtn, gbc);

        // LOGIN LOGIC
        loginBtn.addActionListener(e -> {

            String email = emailField.getText().trim();
            String pass = new String(passField.getPassword());

            if (email.isEmpty() || pass.isEmpty()) {
                msg.setForeground(Color.RED);
                msg.setText("All fields required ❌");
                return;
            }

            try {
                Connection con = DBConnection.getConnection();

                if (con == null) {
                    msg.setText("Database not connected ❌");
                    return;
                }

                String query = "SELECT * FROM users WHERE email=? AND password=?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, email);
                ps.setString(2, pass);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    msg.setForeground(new Color(40, 167, 69));
                    msg.setText("Login Success ✅");

                    // 👉 NEXT PAGE (CHANGE IF NEEDED)
                    cardLayout.show(mainPanel, "HOME");

                } else {
                    msg.setForeground(Color.RED);
                    msg.setText("Invalid email or password ❌");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                msg.setText("Database error ❌");
            }
        });

        // NAVIGATION
        signupBtn.addActionListener(e ->
                cardLayout.show(mainPanel, "SIGNUP")
        );

        panel.add(card);
        return panel;
    }
}
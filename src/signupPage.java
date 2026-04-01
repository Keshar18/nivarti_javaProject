import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class signupPage {

    public static JPanel createPanel(CardLayout cardLayout, JPanel mainPanel) {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 244, 250));

        JPanel card = new JPanel();
        card.setLayout(new GridLayout(0, 1, 10, 10)); // 🔥 FIX: BoxLayout hata diya
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        card.setPreferredSize(new Dimension(350, 380));

        JLabel title = new JLabel("Sign Up");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 30));

        JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(200, 30));

        JButton signupBtn = new JButton("Create Account");
        signupBtn.setBackground(new Color(58, 123, 213));
        signupBtn.setForeground(Color.WHITE);

        JLabel msg = new JLabel(" "); // 🔥 always space reserved
        msg.setForeground(new Color(220, 53, 69));
        msg.setHorizontalAlignment(SwingConstants.CENTER);

        // 🔥 ADD COMPONENTS
        card.add(title);
        card.add(new JLabel("Email"));
        card.add(emailField);
        card.add(new JLabel("Password"));
        card.add(passField);
        card.add(signupBtn);
        card.add(msg);

        // 🔥 ACTION
        signupBtn.addActionListener(e -> {

            String email = emailField.getText().trim();
            String pass = new String(passField.getPassword());

            if (email.isEmpty() || pass.isEmpty()) {
                msg.setText("All fields required ❌");
                return;
            }

            try {
                Connection con = DBConnection.getConnection();

                String query = "INSERT INTO users (email, password) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, email);
                ps.setString(2, pass);

                ps.executeUpdate();

                msg.setForeground(new Color(40, 167, 69));
                msg.setText("Account Created ✅");

            } catch (Exception ex) {
                ex.printStackTrace();
                msg.setText("Error creating account ❌");
            }
        });

        panel.add(card);
        return panel;
    }
}
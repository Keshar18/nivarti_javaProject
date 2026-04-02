import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class signupPage {

    public static JPanel createPanel(CardLayout cardLayout, JPanel mainPanel) {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 244, 250));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
        card.setPreferredSize(new Dimension(350, 420));

        // TITLE
        JLabel title = new JLabel("Sign Up");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // EMAIL
        JLabel emailLabel = new JLabel("Email");
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        // PASSWORD
        JLabel passLabel = new JLabel("Password");
        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        // BUTTON
        JButton signupBtn = new JButton("Sign Up");
        signupBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupBtn.setBackground(new Color(58, 123, 213));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFocusPainted(false);

        // MESSAGE (IMPORTANT FIX)
        JLabel msg = new JLabel(" ");
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        msg.setForeground(new Color(220, 53, 69));

        // BACK BUTTON
        JButton backBtn = new JButton("← Back");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);

        // ACTION
        signupBtn.addActionListener(e -> {

            String email = emailField.getText().trim();
            String pass = new String(passField.getPassword());

            if (email.isEmpty() || pass.isEmpty()) {
                msg.setForeground(new Color(220, 53, 69));
                msg.setText("All fields required ❌");
                return;
            }

            try {
                Connection con = DBConnection.getConnection();

                if (con == null) {
                    msg.setText("Database not connected ❌");
                    return;
                }

                String query = "INSERT INTO users (email, password) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, email);
                ps.setString(2, pass);

                ps.executeUpdate();

                msg.setForeground(new Color(40, 167, 69));
                msg.setText("Account Created ✅");

                emailField.setText("");
                passField.setText("");

            } catch (SQLIntegrityConstraintViolationException ex) {
                msg.setText("Email already exists ❌");
            } catch (Exception ex) {
                ex.printStackTrace();
                msg.setText("Error creating account ❌");
            }
        });

        backBtn.addActionListener(e ->
                cardLayout.show(mainPanel, "LOGIN"
        );

        // ADD COMPONENTS (PERFECT SPACING)
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(emailLabel);
        card.add(emailField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(passLabel);
        card.add(passField);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        card.add(signupBtn);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(msg);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(backBtn);

        panel.add(card);

        return panel;
    }
}
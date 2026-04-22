//import javax.swing.*;
//import java.awt.*;
//import java.sql.*;
//
//public class LoginPage {
//
//    public static JPanel createPanel(CardLayout cardLayout, JPanel mainPanel) {
//
//    	
//        JPanel panel = new JPanel(new GridBagLayout());
//        panel.setBackground(new Color(240, 244, 250));
//
//        JPanel card = new JPanel(new GridBagLayout());
//        card.setBackground(Color.WHITE);
//        card.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
//        card.setPreferredSize(new Dimension(420, 420));
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(8, 0, 8, 0);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 0;
//        gbc.weightx = 1;
//
//        int y = 0;
//
//        // TITLE
//        JLabel title = new JLabel("Login");
//        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
//        title.setHorizontalAlignment(SwingConstants.CENTER);
//        gbc.gridy = y++;
//        card.add(title, gbc);
//
//        // EMAIL
//        JLabel emailLabel = new JLabel("Email");
//        gbc.gridy = y++;
//        card.add(emailLabel, gbc);
//
//        JTextField emailField = new JTextField();
//        emailField.setPreferredSize(new Dimension(300, 35));
//        emailField.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
//        gbc.gridy = y++;
//        card.add(emailField, gbc);
//
//        // PASSWORD
//        JLabel passLabel = new JLabel("Password");
//        gbc.gridy = y++;
//        card.add(passLabel, gbc);
//
//        JPasswordField passField = new JPasswordField();
//        passField.setPreferredSize(new Dimension(300, 35));
//        passField.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
//        gbc.gridy = y++;
//        card.add(passField, gbc);
//
//        // LOGIN BUTTON
//        JButton loginBtn = new JButton("Login");
//        loginBtn.setBackground(new Color(58, 123, 213));
//        loginBtn.setForeground(Color.WHITE);
//        loginBtn.setFocusPainted(false);
//        gbc.gridy = y++;
//        card.add(loginBtn, gbc);
//
//        // MESSAGE
//        JLabel msg = new JLabel(" ");
//        msg.setForeground(Color.RED);
//        msg.setHorizontalAlignment(SwingConstants.CENTER);
//        gbc.gridy = y++;
//        card.add(msg, gbc);
//
//        // SIGNUP
//        JButton signupBtn = new JButton("Create Account");
//        signupBtn.setBorderPainted(false);
//        signupBtn.setContentAreaFilled(false);
//        signupBtn.setForeground(new Color(58, 123, 213));
//        gbc.gridy = y++;
//        card.add(signupBtn, gbc);
//
//        // BACK BUTTON
//        JButton backBtn = new JButton("← Back");
//        backBtn.setBorderPainted(false);
//        backBtn.setContentAreaFilled(false);
//        backBtn.setForeground(Color.GRAY);
//        gbc.gridy = y++;
//        card.add(backBtn, gbc);
//
//        // ================= LOGIN LOGIC =================
//        loginBtn.addActionListener(e -> {
//
//            String email = emailField.getText().trim();
//            String pass = new String(passField.getPassword());
//
//            if (email.isEmpty() || pass.isEmpty()) {
//                msg.setText("All fields required ❌");
//                return;
//            }
//
//            try {
//                Connection con = DBConnection.getConnection();
//
//                if (con == null) {
//                    msg.setText("Database not connected ❌");
//                    return;
//                }
//
//                String query = "SELECT * FROM users WHERE email=? AND password=?";
//                PreparedStatement ps = con.prepareStatement(query);
//
//                ps.setString(1, email);
//                ps.setString(2, pass);
//
//                ResultSet rs = ps.executeQuery();
//
//                if (rs.next()) {
//
//                    Session.userEmail = email;
//
//                    msg.setForeground(new Color(40, 167, 69));
//                    msg.setText("Login Success ✅");
//
//                    // 🔥 ORIGINAL SIMPLE LOGIC
//                    if (email.equalsIgnoreCase("admin@gmail.com") && pass.equals("admin123")) {
//
//                        new AdminPanel(); // ✅ Admin panel open
//                    } 
//                    else if (email.equalsIgnoreCase("authority@gmail.com")) {
//
//                        new AuthorityDashboard(email); // optional
//                    } 
//                    else {
//                        cardLayout.show(mainPanel, "HOME"); // user
//                    }
//
//                    MainGUI.refreshDashboard();
//
//                } else {
//                    msg.setForeground(Color.RED);
//                    msg.setText("Invalid email or password ❌");
//                }
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                msg.setText("Database error ❌");
//            }
//        });
//
//        // NAVIGATION
//        signupBtn.addActionListener(e ->
//                cardLayout.show(mainPanel, "SIGNUP")
//        );
//
//        backBtn.addActionListener(e ->
//                cardLayout.show(mainPanel, "HOME")
//        );
//
//        panel.add(card);
//        return panel;
//    }
//}



import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPage extends JFrame {

    JTextField emailField;
    JPasswordField passField;
    JLabel msg;

    public LoginPage() {

        setTitle("Login");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240));
        panel.setLayout(null);

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(250, 40, 100, 30);
        panel.add(title);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(150, 120, 100, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 150, 300, 30);
        panel.add(emailField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(150, 200, 100, 25);
        panel.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(150, 230, 300, 30);
        panel.add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(150, 280, 300, 35);
        loginBtn.setBackground(new Color(58, 110, 180));
        loginBtn.setForeground(Color.WHITE);
        panel.add(loginBtn);

        JButton signupBtn = new JButton("Create Account");
        signupBtn.setBounds(150, 330, 300, 30);
        signupBtn.setBorderPainted(false);
        signupBtn.setContentAreaFilled(false);
        signupBtn.setForeground(Color.BLUE);
        panel.add(signupBtn);

        JButton backBtn = new JButton("← Back");
        backBtn.setBounds(150, 370, 100, 25);
        panel.add(backBtn);

        msg = new JLabel("");
        msg.setBounds(150, 410, 300, 25);
        panel.add(msg);

        add(panel);

        // 🔥 LOGIN LOGIC (FINAL FIX)
        loginBtn.addActionListener(e -> {

            String email = emailField.getText();
            String pass = new String(passField.getPassword());

            try {
                Connection con = DBConnection.getConnection();

                String query = "SELECT * FROM users WHERE email=? AND password=?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, email);
                ps.setString(2, pass);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    Session.userEmail = email;

                    // 🔥 ADMIN CHECK (MAIN FIX)
                    if (email.equalsIgnoreCase("admin@gmail.com") && pass.equals("admin123")) {
                        Session.role = "admin";
                        new AdminPanel();
                    } else {
                        Session.role = "user";
                        new MainGUI();
                    }

                    dispose();

                } else {
                    msg.setForeground(Color.RED);
                    msg.setText("Invalid email or password ❌");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 🔥 SIGNUP (IMPORTANT: class name same rakho)
        signupBtn.addActionListener(e -> {
            new signupPage();   // ⚠️ small s (tumhari file ke hisaab se)
            dispose();
        });

        // BACK
        backBtn.addActionListener(e -> {
            new MainGUI();
            dispose();
        });

        setVisible(true);
    }
}
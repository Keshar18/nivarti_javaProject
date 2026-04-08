import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class MainGUI {

    //  GLOBAL VARIABLES (IMPORTANT)
    static JButton reportBtn, myBtn, userLoginBtn, adminLoginBtn, logoutBtn;
    static JLabel welcomeLabel;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Nivarti");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // ================= HOME PANEL =================
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
        homePanel.setBackground(new Color(58, 123, 213));

        JLabel heading = new JLabel("Bridge the Gap Between Citizens & Officials");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Report issues and track progress easily");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(Color.WHITE);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomeLabel = new JLabel("");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        reportBtn = new JButton("Add Complaint");
        myBtn = new JButton("My Complaints");
        userLoginBtn = new JButton("User Login");
        adminLoginBtn = new JButton("Admin Login");
        logoutBtn = new JButton("Logout");

        Dimension btnSize = new Dimension(200, 45);

        for (JButton btn : new JButton[]{reportBtn, myBtn, userLoginBtn, adminLoginBtn, logoutBtn}) {
            btn.setMaximumSize(btnSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);
        }

        homePanel.add(Box.createVerticalGlue());
        homePanel.add(heading);
        homePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        homePanel.add(sub);
        homePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        homePanel.add(welcomeLabel);
        homePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        homePanel.add(reportBtn);
        homePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        homePanel.add(myBtn);
        homePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel loginPanel = new JPanel();
        loginPanel.setOpaque(false);
        loginPanel.add(userLoginBtn);
        loginPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        loginPanel.add(adminLoginBtn);

        homePanel.add(loginPanel);
        homePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        homePanel.add(logoutBtn);
        homePanel.add(Box.createVerticalGlue());

        // ================= ADD PANEL =================
        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBackground(new Color(240, 244, 250));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        card.setPreferredSize(new Dimension(350, 400));

        JLabel title = new JLabel("Add Complaint");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField issueField = new JTextField();
        JTextField locationField = new JTextField();
        
        JComboBox<String> categoryBox = new JComboBox<>(
        	    new String[]{"Road", "Water", "Electricity", "Garbage"}
        	);
        	categoryBox.setMaximumSize(new Dimension(320, 40));

        Dimension fieldSize = new Dimension(320, 40);

        for (JTextField field : new JTextField[]{issueField, locationField}) {
            field.setMaximumSize(fieldSize);
            field.setBackground(new Color(245, 247, 250));
            field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        }

        JButton submitBtn = new JButton("Submit");
        JButton backBtn = new JButton("← Back");

        submitBtn.setBackground(new Color(0, 120, 215));
        submitBtn.setForeground(Color.WHITE);

        submitBtn.addActionListener(e -> {

            if (Session.userEmail == null) {
                JOptionPane.showMessageDialog(null, "Please login first!");
                cardLayout.show(mainPanel, "LOGIN");
                return;
            }

            try {
                Connection con = DBConnection.getConnection();

                String query = "INSERT INTO complaints(name, issue, location, category, status) VALUES (?, ?, ?, 'Pending')";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, Session.userEmail);
                ps.setString(2, issueField.getText());
                ps.setString(3, locationField.getText());

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Complaint Submitted ✅");

                issueField.setText("");
                locationField.setText("");

                cardLayout.show(mainPanel, "HOME");
                refreshDashboard();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        backBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "HOME");
            refreshDashboard();
        });

        card.add(title);
        card.add(issueField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(locationField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(new JLabel("Category"));
        card.add(categoryBox);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(submitBtn);
        card.add(backBtn);

        addPanel.add(card);

        // ================= MY PANEL =================
        JPanel myPanel = ViewComplaints.createPanel(cardLayout, mainPanel);

        // ================= BUTTON ACTIONS =================

        reportBtn.addActionListener(e -> {
            if(Session.userEmail == null){
                JOptionPane.showMessageDialog(null, "Please login first!");
                cardLayout.show(mainPanel, "LOGIN");
                return;
            }
            cardLayout.show(mainPanel, "ADD");
        });

        myBtn.addActionListener(e -> {
            if(Session.userEmail == null){
                JOptionPane.showMessageDialog(null, "Please login first!");
                cardLayout.show(mainPanel, "LOGIN");
                return;
            }
            ViewComplaints.loadData(Session.userEmail);
            cardLayout.show(mainPanel, "MY");
        });

        userLoginBtn.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));

        adminLoginBtn.addActionListener(e -> {

            String email = JOptionPane.showInputDialog("Enter Admin Email:");
            String pass = JOptionPane.showInputDialog("Enter Password:");

            if(email != null && pass != null &&
               email.equals("admin@gmail.com") && pass.equals("admin123")){

                Session.userEmail = email;
                Session.role = "admin";

                JOptionPane.showMessageDialog(null, "Admin Login Successful ✅");

                new UpdateStatus(); // 🔥 OPEN ADMIN PANEL

            } else {
                JOptionPane.showMessageDialog(null, "Invalid Admin Credentials ❌");
            }
        });

        logoutBtn.addActionListener(e -> {
            Session.userEmail = null;
            JOptionPane.showMessageDialog(null, "Logged out!");
            cardLayout.show(mainPanel, "HOME");
            refreshDashboard();
        });

        // ================= ADD PANELS =================
        mainPanel.add(homePanel, "HOME");
        mainPanel.add(addPanel, "ADD");
        mainPanel.add(myPanel, "MY");
        mainPanel.add(LoginPage.createPanel(cardLayout, mainPanel), "LOGIN");
        mainPanel.add(signupPage.createPanel(cardLayout, mainPanel), "SIGNUP");

        frame.add(mainPanel);
        frame.setVisible(true);

        // INITIAL STATE
        refreshDashboard();
    }

    // 🔥 DASHBOARD REFRESH METHOD
    public static void refreshDashboard() {

        if(Session.userEmail == null){
            reportBtn.setVisible(false);
            myBtn.setVisible(false);
            logoutBtn.setVisible(false);
            welcomeLabel.setText("");

            userLoginBtn.setVisible(true);
            adminLoginBtn.setVisible(true);

        } else {
            reportBtn.setVisible(true);
            myBtn.setVisible(true);
            logoutBtn.setVisible(true);
            welcomeLabel.setText("Welcome, " + Session.userEmail);

            userLoginBtn.setVisible(false);
            adminLoginBtn.setVisible(false);
        }
        
        // ADMIN OVERRIDE (IMPORTANT)
        if(Session.role != null && Session.role.equals("admin")){
            reportBtn.setVisible(false);
            myBtn.setVisible(false);
        }
    }
}
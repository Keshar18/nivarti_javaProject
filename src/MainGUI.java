import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class MainGUI {

    static JButton reportBtn, myBtn, userLoginBtn, adminLoginBtn, authorityLoginBtn, logoutBtn;
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
        authorityLoginBtn = new JButton("Authority Login");
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

     

   

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        loginPanel.setOpaque(false);

        loginPanel.add(userLoginBtn);
        loginPanel.add(adminLoginBtn);
        loginPanel.add(authorityLoginBtn);

      

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
        card.setPreferredSize(new Dimension(350, 420));

        JLabel title = new JLabel("Add Complaint");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel issueLabel = new JLabel("Enter Complaint:");
        JTextField issueField = new JTextField();

        JLabel locationLabel = new JLabel("Enter Location:");
        JTextField locationField = new JTextField();

        JLabel categoryLabel = new JLabel("Category:");

        JComboBox<String> categoryBox = new JComboBox<>(
                new String[]{"Road", "Water", "Electricity", "Garbage", "Other"}
                
        );
        categoryBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        categoryBox.setMaximumSize(new Dimension(320, 40));
        categoryBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        categoryBox.setMaximumSize(new Dimension(320, 40));
        categoryBox.setPreferredSize(new Dimension(320, 40));
        
        Dimension fieldSize = new Dimension(320, 40);

        for (JTextField field : new JTextField[]{issueField, locationField}) {
        	categoryBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        	issueField.setAlignmentX(Component.CENTER_ALIGNMENT);
        	locationField.setAlignmentX(Component.CENTER_ALIGNMENT);
        	field.setMaximumSize(new Dimension(320, 45));
        	field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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

            String issue = issueField.getText();
            String location = locationField.getText();

            if(issue.isEmpty() || location.isEmpty()){
                JOptionPane.showMessageDialog(null, "Fill all fields ❌");
                return;
            }

            try {
                Connection con = DBConnection.getConnection();

                String query = "INSERT INTO complaints(name, issue, location, category, status) VALUES (?, ?, ?, ?, 'Pending')";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, Session.userEmail);
                ps.setString(2, issue);
                ps.setString(3, location);
                ps.setString(4, categoryBox.getSelectedItem().toString());

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
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(issueLabel);
        card.add(issueField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(locationLabel);
        card.add(locationField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(categoryLabel);
        card.add(categoryBox);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        card.add(submitBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(backBtn);

        addPanel.add(card);

        // ================= MY PANEL =================
        JPanel myPanel = ViewComplaints.createPanel(cardLayout, mainPanel);

        // BUTTON ACTIONS
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

        userLoginBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "LOGIN");
        });

        adminLoginBtn.addActionListener(e -> {

            JTextField emailField = new JTextField();
            JPasswordField passField = new JPasswordField();

            Object[] fields = {
                "Email:", emailField,
                "Password:", passField
            };

            int option = JOptionPane.showConfirmDialog(
                    null, fields, "Admin Login", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {

                String email = emailField.getText();
                String pass = new String(passField.getPassword());

                if (email.equals("admin@gmail.com") && pass.equals("admin123")) {

                    Session.userEmail = "Admin";
                    Session.role = "admin";

                    JOptionPane.showMessageDialog(null, "Admin Login Successful ✅");

                    new AdminPanel(); // 🔥 OPEN ADMIN PANEL

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Admin Credentials ❌");
                }
            }
        });
        
        
        authorityLoginBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter Authority Name");
            if (name != null && !name.isEmpty()) {
                new AuthorityDashboard(name);
            }
        });

        logoutBtn.addActionListener(e -> {
            Session.userEmail = null;
            refreshDashboard();
        });

        // ADD PANELS
        mainPanel.add(homePanel, "HOME");
        mainPanel.add(addPanel, "ADD");
        mainPanel.add(myPanel, "MY");
        mainPanel.add(LoginPage.createPanel(cardLayout, mainPanel), "LOGIN");

        frame.add(mainPanel);
        frame.setVisible(true);

        refreshDashboard();
    }

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
    }
}
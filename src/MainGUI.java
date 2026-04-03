import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;

public class MainGUI {

    static String currentUser = ""; // NEW

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

        JButton reportBtn = new JButton("Add Complaint");
        JButton myBtn = new JButton("My Complaints");
        JButton adminBtn = new JButton("Admin Panel");
        JButton userLoginBtn = new JButton("User Login");
        JButton adminLoginBtn = new JButton("Admin Login");

        Dimension btnSize = new Dimension(200, 45);

        for (JButton btn : new JButton[]{reportBtn, myBtn, userLoginBtn, adminLoginBtn}) {
            btn.setMaximumSize(btnSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);
        }
        homePanel.add(Box.createVerticalGlue());

        homePanel.add(heading);
        homePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        homePanel.add(sub);
        homePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        homePanel.add(reportBtn);
        homePanel.add(Box.createRigidArea(new Dimension(0, 15)));

        homePanel.add(myBtn);
        homePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
     //  NEW PANEL FOR LOGIN BUTTONS
        JPanel loginPanel = new JPanel();
        loginPanel.setOpaque(false);

        loginPanel.add(userLoginBtn);
        loginPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        loginPanel.add(adminLoginBtn);

        homePanel.add(loginPanel);

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

        JTextField nameField = new JTextField();
        JTextField issueField = new JTextField();
        JTextField locationField = new JTextField();

        Dimension fieldSize = new Dimension(320, 40);

        for (JTextField field : new JTextField[]{nameField, issueField, locationField}) {
            field.setMaximumSize(fieldSize);
            field.setBackground(new Color(245, 247, 250));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            field.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        JLabel nameLabel = new JLabel("Full Name");
        JLabel issueLabel = new JLabel("Describe Issue");
        JLabel locationLabel = new JLabel("Location");

        for (JLabel label : new JLabel[]{nameLabel, issueLabel, locationLabel}) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 13));
            label.setForeground(new Color(80, 80, 80));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        JButton submitBtn = new JButton("Submit");
        JButton backBtn = new JButton("← Back");

        submitBtn.setBackground(new Color(0, 120, 215));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setMaximumSize(new Dimension(260, 40));
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ================= SUBMIT LOGIC =================
        submitBtn.addActionListener(e -> {

            String name = nameField.getText();
            String issue = issueField.getText();
            String location = locationField.getText();
            
            // IMPORTANT FIX
            currentUser = name;

            if(name.isEmpty() || issue.isEmpty() || location.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields!");
                return;
            }

            try {
                Connection con = DBConnection.getConnection();

                if(con == null) {
                    JOptionPane.showMessageDialog(null, "Database not connected ❌");
                    return;
                }

                String query = "INSERT INTO complaints(name, issue, location, status) VALUES (?, ?, ?, 'Pending')";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, name.trim().toLowerCase());
                ps.setString(2, issue);
                ps.setString(3, location);

                int result = ps.executeUpdate();

                if(result > 0) {
                    JOptionPane.showMessageDialog(null, "Complaint Submitted Successfully ✅");

                    // SAVE USER
                    currentUser = name;

                    // CLEAR
                    nameField.setText("");
                    issueField.setText("");
                    locationField.setText("");

                    // GO HOME
                    cardLayout.show(mainPanel, "HOME");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        // Layout
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(nameLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(nameField);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(issueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(issueField);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(locationLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(locationField);

        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(submitBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(backBtn);

        addPanel.add(card);

        // ================= MY PANEL =================
        JPanel myPanel = ViewComplaints.createPanel(cardLayout, mainPanel);

        // ================= NAVIGATION =================
        reportBtn.addActionListener(e -> {

            if(Session.userEmail == null){
                JOptionPane.showMessageDialog(null, "Please login first!");
                cardLayout.show(mainPanel, "LOGIN");
                return;
            }

            cardLayout.show(mainPanel, "ADD");
        });

        myBtn.addActionListener(e -> {

            if(currentUser.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please submit a complaint first!");
                return;
            }
            
            

            ViewComplaints.loadData(currentUser);
            cardLayout.show(mainPanel, "MY");
        });
        
        userLoginBtn.addActionListener(e -> 
        cardLayout.show(mainPanel, "LOGIN")
    );

    adminLoginBtn.addActionListener(e -> 
        JOptionPane.showMessageDialog(null, "Admin Login Coming Soon 🔒")
    );
        
        adminBtn.addActionListener(e -> new UpdateStatus());

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        
        mainPanel.add(homePanel, "HOME");
        mainPanel.add(addPanel, "ADD");
        mainPanel.add(myPanel, "MY");
        mainPanel.add(LoginPage.createPanel(cardLayout, mainPanel), "LOGIN");
        mainPanel.add(signupPage.createPanel(cardLayout, mainPanel), "SIGNUP");

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
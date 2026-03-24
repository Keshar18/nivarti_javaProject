import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class MainGUI {

    static CardLayout cardLayout;
    static JPanel mainPanel;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Nivarti");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // ================= CARD LAYOUT =================
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // ================= HOME PANEL =================
        JPanel homePanel = new JPanel(new BorderLayout());

        // ---------- NAVBAR ----------
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(new Color(0, 150, 200));
        navbar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel logo = new JLabel("NIVARTI");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logo.setForeground(Color.WHITE);

        JPanel navButtons = new JPanel();
        navButtons.setOpaque(false);

        JButton loginBtn = new JButton("Login");
        JButton adminBtn = new JButton("Admin");

        navButtons.add(loginBtn);
        navButtons.add(adminBtn);

        navbar.add(logo, BorderLayout.WEST);
        navbar.add(navButtons, BorderLayout.EAST);

        // ---------- HERO ----------
        JPanel hero = new JPanel();
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setBackground(new Color(58, 123, 213));
        hero.setPreferredSize(new Dimension(900, 350));

        JLabel heading = new JLabel("Bridge the Gap Between Citizens & Officials");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Report issues and track progress easily");
        sub.setForeground(Color.WHITE);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton reportBtn = new JButton("Report Issue");
        JButton browseBtn = new JButton("Browse Officials");

        // button size
        reportBtn.setPreferredSize(new Dimension(200, 45));
        browseBtn.setPreferredSize(new Dimension(200, 45));

        reportBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        browseBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        reportBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        browseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        reportBtn.setFocusPainted(false);
        browseBtn.setFocusPainted(false);

        // spacing
        hero.add(Box.createVerticalGlue());
        hero.add(heading);
        hero.add(Box.createRigidArea(new Dimension(0, 15)));
        hero.add(sub);
        hero.add(Box.createRigidArea(new Dimension(0, 25)));
        hero.add(reportBtn);
        hero.add(Box.createRigidArea(new Dimension(0, 15)));
        hero.add(browseBtn);
        hero.add(Box.createVerticalGlue());

        // ---------- STATS ----------
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 40, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 40, 60));
        statsPanel.setBackground(new Color(58, 123, 213));

        statsPanel.add(createStatCard("50+", "States & Cities"));
        statsPanel.add(createStatCard("100+", "Engineers"));
        statsPanel.add(createStatCard("10+", "Awards"));

        // add to home
        homePanel.add(navbar, BorderLayout.NORTH);
        homePanel.add(hero, BorderLayout.CENTER);
        homePanel.add(statsPanel, BorderLayout.SOUTH);

        // ================= ADD PANEL =================
        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBackground(new Color(240, 245, 250)); // light bg

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== CARD PANEL =====
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        // ===== TITLE =====
        JLabel title = new JLabel("Add Complaint");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== INPUT FIELDS =====
        JTextField nameField = new JTextField();
        JTextField issueField = new JTextField();
        JTextField locationField = new JTextField();

        Dimension fieldSize = new Dimension(300, 35);

        nameField.setMaximumSize(fieldSize);
        issueField.setMaximumSize(fieldSize);
        locationField.setMaximumSize(fieldSize);

     // ===== STYLE COLORS =====
        Color primary = new Color(0, 120, 215);
        Color inputBg = new Color(245, 247, 250);

        // ===== INPUT FIELDS =====
        JTextField nameField = new JTextField();
        JTextField issueField = new JTextField();
        JTextField locationField = new JTextField();

        Dimension fieldSize = new Dimension(300, 40);

        // styling inputs
        for (JTextField field : new JTextField[]{nameField, issueField, locationField}) {
            field.setMaximumSize(fieldSize);
            field.setBackground(inputBg);
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

        // ===== LABELS =====
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Color labelColor = new Color(100, 100, 100);

        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(labelColor);

        JLabel issueLabel = new JLabel("Describe Issue");
        issueLabel.setFont(labelFont);
        issueLabel.setForeground(labelColor);

        JLabel locationLabel = new JLabel("Location");
        locationLabel.setFont(labelFont);
        locationLabel.setForeground(labelColor);

        // ===== BUTTON =====
        JButton submitBtn = new JButton("Submit");
        submitBtn.setBackground(new Color(0, 120, 215));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submitBtn.setMaximumSize(new Dimension(200, 40));
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== BACK BUTTON =====
        JButton backBtn = new JButton("← Back");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== ADD COMPONENTS =====
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        card.add(nameLabel);
        card.add(nameField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(issueLabel);
        card.add(issueField);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        card.add(locationLabel);
        card.add(locationField);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        card.add(submitBtn);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(backBtn);

        // CENTER CARD
        addPanel.add(card);

        // ===== ACTIONS =====
        submitBtn.addActionListener(e -> {
            String name = nameField.getText();
            String issue = issueField.getText();
            String location = locationField.getText();

            try {
                Connection con = DBConnection.getConnection();
                String query = "INSERT INTO complaints(name, issue, location, status) VALUES (?, ?, ?, 'Pending')";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, name);
                ps.setString(2, issue);
                ps.setString(3, location);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Complaint Submitted!");

                nameField.setText("");
                issueField.setText("");
                locationField.setText("");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // BACK BUTTON
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        // ADD TO MAIN PANEL
        mainPanel.add(addPanel, "ADD");

        // ================= VIEW PANEL =================
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));

        JButton backFromView = new JButton("← Back");
        backFromView.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel viewLabel = new JLabel("View Complaints Screen");
        viewLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        viewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        viewPanel.add(Box.createVerticalGlue());
        viewPanel.add(viewLabel);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        viewPanel.add(backFromView);
        viewPanel.add(Box.createVerticalGlue());

        // ================= ADD TO CARD =================
        mainPanel.add(homePanel, "HOME");
        mainPanel.add(addPanel, "ADD");
        mainPanel.add(viewPanel, "VIEW");

        // ================= BUTTON ACTIONS =================
        reportBtn.addActionListener(e -> cardLayout.show(mainPanel, "ADD"));
        browseBtn.addActionListener(e -> cardLayout.show(mainPanel, "VIEW"));

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        backFromView.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        // ================= FRAME =================
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // ================= CARD DESIGN =================
    public static JPanel createStatCard(String number, String label) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel icon = new JLabel("🌍");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel num = new JLabel(number);
        num.setFont(new Font("Segoe UI", Font.BOLD, 24));
        num.setForeground(new Color(58, 123, 213));
        num.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel text = new JLabel(label);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(icon);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(num);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(text);

        return card;
    }
}
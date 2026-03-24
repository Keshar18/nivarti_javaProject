import javax.swing.*;
import java.awt.*;

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
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));

        JButton backFromAdd = new JButton("← Back");
        backFromAdd.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel addLabel = new JLabel("Add Complaint Screen");
        addLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        addLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        addPanel.add(Box.createVerticalGlue());
        addPanel.add(addLabel);
        addPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addPanel.add(backFromAdd);
        addPanel.add(Box.createVerticalGlue());

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

        backFromAdd.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
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
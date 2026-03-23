import javax.swing.*;
import java.awt.*;

public class MainGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Nivarti");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout());

        // ================= NAVBAR =================
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

        // ================= HERO =================
        JPanel hero = new JPanel();
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setBackground(new Color(58, 123, 213));
        hero.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        JLabel heading = new JLabel("Bridge the Gap Between Citizens & Officials");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Report issues and track progress easily");
        sub.setForeground(Color.WHITE);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton reportBtn = new JButton("Report Issue");
        JButton browseBtn = new JButton("Browse Officials");
        reportBtn.setPreferredSize(new Dimension(180, 40));
        browseBtn.setPreferredSize(new Dimension(180, 40));

        reportBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        browseBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        reportBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        browseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        hero.add(heading);
        hero.add(Box.createRigidArea(new Dimension(0, 15)));
        hero.add(sub);
        hero.add(Box.createRigidArea(new Dimension(0, 25)));
        hero.add(reportBtn);
        hero.add(Box.createRigidArea(new Dimension(0, 15)));
        hero.add(browseBtn);
        hero.setPreferredSize(new Dimension(900, 350));
        reportBtn.setFocusPainted(false);
        browseBtn.setFocusPainted(false);

        // ================= STATS =================
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        statsPanel.setBackground(new Color(58, 123, 213)); // same as hero

        statsPanel.add(createStatCard("50+", "States & Cities"));
        statsPanel.add(createStatCard("100+", "Engineers"));
        statsPanel.add(createStatCard("10+", "Awards"));

        // ================= ACTIONS =================
        reportBtn.addActionListener(e -> new AddComplaint());

        browseBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(frame, "Enter your name:");
            if (name != null && !name.trim().isEmpty()) {
                new ViewComplaints(name);
            }
        });

        // ================= ADD TO FRAME =================
        mainPanel.add(navbar, BorderLayout.NORTH);
        mainPanel.add(hero, BorderLayout.CENTER);
        mainPanel.add(statsPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    //  CARD CREATOR METHOD
    public static JPanel createStatCard(String number, String label) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(255, 255, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // NUMBER
        JLabel num = new JLabel(number);
        num.setFont(new Font("Segoe UI", Font.BOLD, 24));
        num.setForeground(new Color(58, 123, 213));
        num.setAlignmentX(Component.CENTER_ALIGNMENT);

        // LABEL
        JLabel text = new JLabel(label);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        text.setForeground(Color.DARK_GRAY);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ICON 
        JLabel icon = new JLabel("🌍");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(icon);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(num);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(text);

        return card;
    }
}
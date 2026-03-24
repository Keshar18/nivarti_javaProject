import javax.swing.*;
import java.awt.*;

public class MainGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Nivarti");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // ================= MAIN PANEL =================
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
        
        JPanel viewPanel = ViewComplaints.createPanel(cardLayout, mainPanel);
        mainPanel.add(viewPanel, "VIEW");

        JButton reportBtn = new JButton("Report Issue");
        JButton browseBtn = new JButton("Browse Officials");

        Dimension btnSize = new Dimension(180, 40);

        reportBtn.setMaximumSize(btnSize);
        browseBtn.setMaximumSize(btnSize);

        reportBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        browseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        browseBtn.addActionListener(e -> cardLayout.show(mainPanel, "VIEW"));

        homePanel.add(Box.createVerticalGlue());
        homePanel.add(heading);
        homePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        homePanel.add(sub);
        homePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        homePanel.add(reportBtn);
        homePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        homePanel.add(browseBtn);
        homePanel.add(Box.createVerticalGlue());

        // ================= ADD COMPLAINT PANEL =================
        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBackground(new Color(240, 244, 250));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
   
        card.setPreferredSize(new Dimension(350, 420));
        card.setMaximumSize(new Dimension(420, 500));

        // Title
        JLabel title = new JLabel("Add Complaint");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Input fields
        JTextField nameField = new JTextField();
        JTextField issueField = new JTextField();
        JTextField locationField = new JTextField();

        Dimension fieldSize = new Dimension(320, 40);

        nameField.setMaximumSize(fieldSize);
        issueField.setMaximumSize(fieldSize);
        locationField.setMaximumSize(fieldSize);

        // Styling
        Color inputBg = new Color(245, 247, 250);

        for (JTextField field : new JTextField[]{nameField, issueField, locationField}) {
            field.setBackground(inputBg);
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            field.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        // Labels
        JLabel nameLabel = new JLabel("Full Name");
        JLabel issueLabel = new JLabel("Describe Issue");
        JLabel locationLabel = new JLabel("Location");

        for (JLabel label : new JLabel[]{nameLabel, issueLabel, locationLabel}) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 13));
            label.setForeground(new Color(80, 80, 80));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        // Buttons
        JButton submitBtn = new JButton("Submit");
        JButton backBtn = new JButton("← Back");

        submitBtn.setBackground(new Color(0, 120, 215));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setMaximumSize(new Dimension(260, 40));
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        // ================= NAVIGATION =================
        reportBtn.addActionListener(e -> cardLayout.show(mainPanel, "ADD"));
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        // ================= ADD TO MAIN =================
        mainPanel.add(homePanel, "HOME");
        mainPanel.add(addPanel, "ADD");

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
import java.awt.*;
import javax.swing.*;

public class AddComplaint {

    public AddComplaint() {

        JFrame frame = new JFrame("Add Complaint");
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // Background Gradient Panel
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color c1 = new Color(58, 123, 213);
                Color c2 = new Color(0, 210, 255);
                GradientPaint gp = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        // Card Panel
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(150, 50, 300, 320);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Title
        JLabel title = new JLabel("Add Complaint");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBounds(80, 20, 200, 30);

        // Fields
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(30, 70, 100, 20);

        JTextField nameField = new JTextField();
        nameField.setBounds(30, 90, 240, 30);

        JLabel issueLabel = new JLabel("Issue");
        issueLabel.setBounds(30, 130, 100, 20);

        JTextField issueField = new JTextField();
        issueField.setBounds(30, 150, 240, 30);

        JLabel locationLabel = new JLabel("Location");
        locationLabel.setBounds(30, 190, 100, 20);

        JTextField locationField = new JTextField();
        locationField.setBounds(30, 210, 240, 30);

        // Submit Button
        JButton submitBtn = new JButton("Submit");
        submitBtn.setBounds(80, 260, 140, 35);
        submitBtn.setBackground(new Color(0, 123, 255));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Add all to card
        card.add(title);
        card.add(nameLabel);
        card.add(nameField);
        card.add(issueLabel);
        card.add(issueField);
        card.add(locationLabel);
        card.add(locationField);
        card.add(submitBtn);

        panel.add(card);
        frame.add(panel);

        frame.setVisible(true);
    }
}
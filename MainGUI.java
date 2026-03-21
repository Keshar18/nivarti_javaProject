import java.awt.*;
import javax.swing.*;

public class MainGUI {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Nivarti");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel 
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
        card.setBounds(150, 80, 300, 250);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Title
        JLabel title = new JLabel("Nivarti");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(100, 20, 150, 30);

        JLabel subtitle = new JLabel("Smart Civic System");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setBounds(95, 45, 200, 20);

        // Buttons
        JButton addBtn = new JButton("Add Complaint");
        addBtn.setBounds(50, 90, 200, 35);
        addBtn.setBackground(new Color(0, 123, 255));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JButton viewBtn = new JButton("View Complaints");
        viewBtn.setBounds(50, 140, 200, 35);
        viewBtn.setBackground(new Color(40, 167, 69));
        viewBtn.setForeground(Color.WHITE);
        viewBtn.setFocusPainted(false);
        viewBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Action
        addBtn.addActionListener(e -> new AddComplaint());

        // Added elements
        card.add(title);
        card.add(subtitle);
        card.add(addBtn);
        card.add(viewBtn);

        panel.add(card);
        frame.add(panel);
        frame.setVisible(true);
    }
}
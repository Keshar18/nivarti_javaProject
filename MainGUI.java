import java.awt.*;
import javax.swing.*;

public class MainGUI {
    public static void main(String[] args) {

        // Frame
        JFrame frame = new JFrame("Nivarti - Smart Civic System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 248, 255)); // light blue
        panel.setLayout(null);

        // Title
        JLabel title = new JLabel("NIVARTI");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(180, 30, 200, 40);
        panel.add(title);

        JLabel subtitle = new JLabel("Smart Complaint System");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setBounds(170, 60, 200, 20);
        panel.add(subtitle);

        // Buttons
        JButton addBtn = new JButton("Add Complaint");
        addBtn.setBounds(150, 120, 200, 40);
        addBtn.setBackground(new Color(0, 123, 255));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setFont(new Font("Arial", Font.BOLD, 14));

        JButton viewBtn = new JButton("View Complaints");
        viewBtn.setBounds(150, 180, 200, 40);
        viewBtn.setBackground(new Color(40, 167, 69));
        viewBtn.setForeground(Color.WHITE);
        viewBtn.setFocusPainted(false);
        viewBtn.setFont(new Font("Arial", Font.BOLD, 14));

        addBtn.addActionListener(e -> {
    new AddComplaint();
});

        panel.add(addBtn);
        panel.add(viewBtn);

        frame.add(panel);
        frame.setVisible(true);
    }
}
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddComplaint {

    JFrame frame;
    JTextField nameField, issueField, locationField;

    public AddComplaint() {

        frame = new JFrame("Add Complaint");
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Gradient Background Panel
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

        //  MODERN CARD 
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(75, 50, 350, 300);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));

        // Title
        JLabel title = new JLabel("Add Complaint");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(90, 15, 200, 30);

        // Fields
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 70, 80, 25);

        nameField = new JTextField();
        nameField.setBounds(120, 70, 180, 30);

        JLabel issueLabel = new JLabel("Issue:");
        issueLabel.setBounds(30, 110, 80, 25);

        issueField = new JTextField();
        issueField.setBounds(120, 110, 180, 30);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setBounds(30, 150, 80, 25);

        locationField = new JTextField();
        locationField.setBounds(120, 150, 180, 30);

        //  Modern Button
        JButton submitBtn = new JButton("Submit");
        submitBtn.setBounds(110, 210, 120, 35);
        submitBtn.setBackground(new Color(58, 123, 213));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);

        //  BUTTON LOGIC
        submitBtn.addActionListener(e -> {

            System.out.println("CLICKED"); // debug

            String name = nameField.getText();
            String issue = issueField.getText();
            String location = locationField.getText();

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

                ps.setString(1, name);
                ps.setString(2, issue);
                ps.setString(3, location);

                int rows = ps.executeUpdate();

                if(rows > 0) {
                    JOptionPane.showMessageDialog(null, "Complaint Submitted Successfully! ✅");
                }

                // clear fields
                nameField.setText("");
                issueField.setText("");
                locationField.setText("");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
       

        // Add components
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
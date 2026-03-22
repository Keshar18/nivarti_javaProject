import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddComplaint {

    JFrame frame;
    JTextField nameField, issueField, locationField;

    public AddComplaint() {

        frame = new JFrame("Add Complaint");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Add Complaint");
        title.setBounds(120, 20, 200, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.BLACK);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 80, 100, 25);

        nameField = new JTextField();
        nameField.setBounds(150, 80, 150, 25);

        JLabel issueLabel = new JLabel("Issue:");
        issueLabel.setBounds(50, 120, 100, 25);

        issueField = new JTextField();
        issueField.setBounds(150, 120, 150, 25);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setBounds(50, 160, 100, 25);

        locationField = new JTextField();
        locationField.setBounds(150, 160, 150, 25);

        JButton submitBtn = new JButton("Submit");
        submitBtn.setBounds(130, 220, 120, 35);
        submitBtn.setBackground(new Color(0, 123, 255));
        submitBtn.setForeground(Color.WHITE);

        //  ACTION BUTTON
        submitBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String issue = issueField.getText();
                String location = locationField.getText();

                String query = "INSERT INTO complaints(name, issue, location, status) VALUES (?, ?, ?, ?)";

                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, name);
                ps.setString(2, issue);
                ps.setString(3, location);
                ps.setString(4, "Pending");

                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Complaint Submitted Successfully!");

                // clear fields
                nameField.setText("");
                issueField.setText("");
                locationField.setText("");

                // close current window
                frame.dispose();

                // open view page
                new ViewComplaints();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        panel.add(title);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(issueLabel);
        panel.add(issueField);
        panel.add(locationLabel);
        panel.add(locationField);
        panel.add(submitBtn);

        frame.add(panel);
        frame.setVisible(true);
    }
}
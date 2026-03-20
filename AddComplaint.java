import javax.swing.*;

public class AddComplaint {

    public AddComplaint() {

        JFrame frame = new JFrame("Add Complaint");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // Labels
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 25);

        JLabel issueLabel = new JLabel("Issue:");
        issueLabel.setBounds(50, 100, 100, 25);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setBounds(50, 150, 100, 25);

        // Fields
        JTextField nameField = new JTextField();
        nameField.setBounds(150, 50, 150, 25);

        JTextField issueField = new JTextField();
        issueField.setBounds(150, 100, 150, 25);

        JTextField locationField = new JTextField();
        locationField.setBounds(150, 150, 150, 25);

        // Button
        JButton submitBtn = new JButton("Submit");
        submitBtn.setBounds(130, 220, 120, 30);

        // Add to frame
        frame.add(nameLabel);
        frame.add(issueLabel);
        frame.add(locationLabel);
        frame.add(nameField);
        frame.add(issueField);
        frame.add(locationField);
        frame.add(submitBtn);

        frame.setVisible(true);
    }
}
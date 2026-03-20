import javax.swing.*;

public class MainGUI {
    public static void main(String[] args) {
        
        // Create Frame (Window)
        JFrame frame = new JFrame("Nivarti - Complaint System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create Button 1
        JButton addBtn = new JButton("Add Complaint");
        addBtn.setBounds(120, 80, 150, 30);

        // Create Button 2
        JButton viewBtn = new JButton("View Complaints");
        viewBtn.setBounds(120, 130, 150, 30);

        // Add buttons to frame
        frame.add(addBtn);
        frame.add(viewBtn);

        // Make frame visible
        frame.setVisible(true);
    }
}
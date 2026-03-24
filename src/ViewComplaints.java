import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewComplaints {

    public static JPanel createPanel(CardLayout cardLayout, JPanel mainPanel) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 244, 250));

        // Title
        JLabel title = new JLabel("View Complaints");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(title, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Name", "Issue", "Location", "Status"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Dummy data (for now)
        model.addRow(new Object[]{1, "Aditya", "Street Light Broken", "Delhi", "Pending"});
        model.addRow(new Object[]{2, "Rahul", "Water Leakage", "Mumbai", "Resolved"});
        model.addRow(new Object[]{3, "Priya", "Garbage Issue", "Pune", "Pending"});

        // Back button
        JButton backBtn = new JButton("← Back");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        JPanel bottom = new JPanel();
        bottom.add(backBtn);

        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.*;

public class updateStatus extends JFrame {

    String selectedCategory = "All";
    String selectedStatus = "All";

    JTable table;
    DefaultTableModel model;

    JLabel totalLabel, pendingLabel, progressLabel, resolvedLabel;

    public updateStatus() {

        setTitle("Admin Panel");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔥 TOP PANEL
        JPanel topPanel = new JPanel();

        JComboBox<String> categoryFilter = new JComboBox<>(
                new String[]{"All", "Road", "Water", "Electricity", "Garbage"}
        );

        topPanel.add(new JLabel("Category:"));
        topPanel.add(categoryFilter);

        categoryFilter.addActionListener(e -> {
            selectedCategory = categoryFilter.getSelectedItem().toString();
            loadData();
        });

        JTextField searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");

        JButton allBtn = new JButton("All");
        JButton pendingBtn = new JButton("Pending");
        JButton progressBtn = new JButton("In Progress");
        JButton resolvedBtn = new JButton("Resolved");

        topPanel.add(new JLabel("Search: "));
        topPanel.add(searchField);
        topPanel.add(searchBtn);

        topPanel.add(allBtn);
        topPanel.add(pendingBtn);
        topPanel.add(progressBtn);
        topPanel.add(resolvedBtn);

        add(topPanel, BorderLayout.NORTH);

        // 🔥 TABLE
        String[] columns = {"ID", "Name", "Issue", "Location", "Category", "Status", "Priority", "Resolved By"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                String status = value.toString();

                if (status.equalsIgnoreCase("Pending")) {
                    c.setBackground(new Color(255, 235, 59));
                } else if (status.equalsIgnoreCase("In Progress")) {
                    c.setBackground(new Color(33, 150, 243));
                } else if (status.equalsIgnoreCase("Resolved")) {
                    c.setBackground(new Color(76, 175, 80));
                } else {
                    c.setBackground(Color.WHITE);
                }

                return c;
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // 🔥 BOTTOM PANEL
        JPanel bottomPanel = new JPanel();

        JButton updateBtn = new JButton("Update Status");

        totalLabel = new JLabel("Total: 0");
        pendingLabel = new JLabel("Pending: 0");
        progressLabel = new JLabel("In Progress: 0");
        resolvedLabel = new JLabel("Resolved: 0");

        bottomPanel.add(updateBtn);
        bottomPanel.add(totalLabel);
        bottomPanel.add(pendingLabel);
        bottomPanel.add(progressLabel);
        bottomPanel.add(resolvedLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        // 🔥 LOAD DATA
        loadData();

        // 🔥 FILTER BUTTONS
        allBtn.addActionListener(e -> { selectedStatus = "All"; loadData(); });
        pendingBtn.addActionListener(e -> { selectedStatus = "Pending"; loadData(); });
        progressBtn.addActionListener(e -> { selectedStatus = "In Progress"; loadData(); });
        resolvedBtn.addActionListener(e -> { selectedStatus = "Resolved"; loadData(); });

        searchBtn.addActionListener(e -> searchData(searchField.getText()));

        updateBtn.addActionListener(e -> updateStatus());

        setVisible(true);
    }

    // 🔥 LOAD DATA
    public void loadData() {
        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("DB NOT CONNECTED ❌");
                return;
            }

            String query = "SELECT * FROM complaints";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("issue"),
                        rs.getString("location"),
                        rs.getString("category"),
                        rs.getString("status"),
                        rs.getString("priority"),
                        rs.getString("resolved_by")
                });
            }

            updateStats();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 SEARCH
    public void searchData(String keyword) {
        try {
            Connection con = DBConnection.getConnection();

            if (con == null) return;

            String query = "SELECT * FROM complaints WHERE issue LIKE ? OR location LIKE ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("issue"),
                        rs.getString("location"),
                        rs.getString("category"),
                        rs.getString("status"),
                        rs.getString("priority"),
                        rs.getString("resolved_by")
                });
            }

            updateStats();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 UPDATE STATUS
    public void updateStatus() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a complaint first!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        String[] options = {"Pending", "In Progress", "Resolved (Pending)"};
        String status = "Resolved (Pending)";

        String admin = JOptionPane.showInputDialog("Enter your name:");

        if (status != null && admin != null) {
            try {
                Connection con = DBConnection.getConnection();

                if (con == null) return;

                String query = "UPDATE complaints SET status=?, resolved_by=? WHERE id=?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, status);
                ps.setString(2, admin);
                ps.setInt(3, id);

                ps.executeUpdate();

                loadData();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 🔥 STATS
    public void updateStats() {

        int total = model.getRowCount();
        int pending = 0, progress = 0, resolved = 0;

        for (int i = 0; i < total; i++) {
            String status = model.getValueAt(i, 5).toString();

            if (status.equalsIgnoreCase("Pending")) pending++;
            else if (status.equalsIgnoreCase("In Progress")) progress++;
            else if (status.equalsIgnoreCase("Resolved")) resolved++;
        }

        totalLabel.setText("Total: " + total);
        pendingLabel.setText("Pending: " + pending);
        progressLabel.setText("In Progress: " + progress);
        resolvedLabel.setText("Resolved: " + resolved);
    }
}


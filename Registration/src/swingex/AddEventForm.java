package swingex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddEventForm extends JFrame {
    private JPanel contentPane;
    private JTextField txtEventName, txtEventDate, txtTicketPrice;

    public AddEventForm() {
        // Set up the frame properties
        setTitle("Add Event");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Maximize window but keep the title bar and window controls
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
        setUndecorated(false); // Ensure window controls (close, minimize) are visible
        setResizable(true); // Allow resizing

        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBackground(new Color(245, 245, 220)); // Beige background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around components
        gbc.anchor = GridBagConstraints.WEST; // Align text to the left

        // Heading Label
        JLabel headingLabel = new JLabel("Add Event");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 48)); // Larger font size
        headingLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Make the heading span two columns
        gbc.insets = new Insets(40, 5, 40, 5); // Adjust padding for heading
        contentPane.add(headingLabel, gbc);

        // Event Name Label and TextField
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(new JLabel("Event Name:"), gbc);

        txtEventName = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtEventName, gbc);

        // Event Date Label and TextField
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(new JLabel("Event Date:"), gbc);

        txtEventDate = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtEventDate, gbc);

        // Ticket Price Label and TextField
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPane.add(new JLabel("Ticket Price:"), gbc);

        txtTicketPrice = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtTicketPrice, gbc);

        // Add Event Button
        JButton btnAddEvent = new JButton("Add Event");
        btnAddEvent.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1; // Set the grid width to 1
        contentPane.add(btnAddEvent, gbc);

        // Back Button
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 1;
        contentPane.add(btnBack, gbc);

        // Action listener for adding event
        btnAddEvent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEventToDatabase();
            }
        });

        // Action listener for going back to Admin Dashboard
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current Add Event window
                dispose();
                
                // Open the Admin Dashboard window
                new AdminPage(); // Replace with actual admin dashboard class name
            }
        });

        setVisible(true); // Make the frame visible
    }

    private void addEventToDatabase() {
        String eventName = txtEventName.getText();
        String eventDate = txtEventDate.getText();
        String ticketPrice = txtTicketPrice.getText();

        if (eventName.isEmpty() || eventDate.isEmpty() || ticketPrice.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        try (Connection con = createConnection()) {
            String query = "INSERT INTO event_details (event_name, event_date, ticket_price) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, eventName);
            ps.setString(2, eventDate);
            ps.setString(3, ticketPrice);
            ps.executeUpdate();

            // Show success message
            JOptionPane.showMessageDialog(this, "Event Added Successfully!");

            // Clear the text fields after adding the event
            txtEventName.setText("");
            txtEventDate.setText("");
            txtTicketPrice.setText("");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding event. Please try again.");
        }
    }

    private static Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/users", "root", "Abi");
    }

    public static void main(String[] args) {
        new AddEventForm(); // Open the Add Event form
    }
}

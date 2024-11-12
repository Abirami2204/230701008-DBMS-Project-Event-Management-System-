package swingex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminPage extends JFrame {
    private JPanel contentPane;

    public AdminPage() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(245, 245, 220)); // Beige background

        // Heading Label
        JLabel headingLabel = new JLabel("Admin Dashboard");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 32));
        headingLabel.setForeground(Color.BLACK);
        headingLabel.setBounds(350, 20, 300, 40);
        contentPane.add(headingLabel);

        // Add Event Button
        JButton btnAdd = new JButton("Add Event");
        btnAdd.setBounds(50, 100, 200, 40);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 16));
        btnAdd.setBackground(Color.WHITE);
        contentPane.add(btnAdd);

        // Modify Event Button
        JButton btnModify = new JButton("Modify Event");
        btnModify.setBounds(50, 160, 200, 40);
        btnModify.setFont(new Font("Arial", Font.BOLD, 16));
        btnModify.setBackground(Color.WHITE);
        contentPane.add(btnModify);

        // Cancel Event Button
        JButton btnDelete = new JButton("Cancel Event");
        btnDelete.setBounds(50, 220, 200, 40);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 16));
        btnDelete.setBackground(Color.WHITE);
        contentPane.add(btnDelete);

        // Remove View Registrations Button
        // Removed the View Registrations button

        // Add Event Form
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddEventForm(); // Open AddEventForm when Add Event button is clicked
            }
        });

        // Modify Event Form
        btnModify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyEvent(); // Modify event functionality
            }
        });

        // Delete Event functionality
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String eventName = JOptionPane.showInputDialog("Enter the event name to delete:");
                if (eventName != null) {
                    deleteEvent(eventName);
                }
            }
        });
    }

    public static void main(String[] args) {
        new AdminPage(); // Create and show the AdminPage window
    }

    private void deleteEvent(String eventName) {
        try (Connection conn = createConnection()) {
            String sql = "DELETE FROM event_details WHERE event_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, eventName);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Event " + eventName + " deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Event not found.");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting event.");
        }
    }

    private void modifyEvent() {
        // Ask for the event name to modify
        String eventName = JOptionPane.showInputDialog("Enter the event name to modify:");

        if (eventName != null && !eventName.isEmpty()) {
            // Show input fields to modify the event details
            JTextField txtEventDate = new JTextField(20);
            JTextField txtTicketPrice = new JTextField(20);

            Object[] message = {
                "Event Date:", txtEventDate,
                "Ticket Price:", txtTicketPrice
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Modify Event - " + eventName, JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String newEventDate = txtEventDate.getText();
                String newTicketPrice = txtTicketPrice.getText();

                if (newEventDate.isEmpty() || newTicketPrice.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in both fields.");
                    return;
                }

                // Update the event details in the database
                updateEvent(eventName, newEventDate, newTicketPrice);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Event name cannot be empty.");
        }
    }

    private void updateEvent(String eventName, String newEventDate, String newTicketPrice) {
        try (Connection conn = createConnection()) {
            String sql = "UPDATE event_details SET event_date = ?, ticket_price = ? WHERE event_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newEventDate);
            stmt.setString(2, newTicketPrice);
            stmt.setString(3, eventName);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Event updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Event not found.");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating event.");
        }
    }

    private Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/users", "root", "Abi");
    }
}

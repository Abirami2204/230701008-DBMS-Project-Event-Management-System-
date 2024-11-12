package swingex;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.sql.*;

public class RegistrationForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtName, txtAddress, txtAge, txtmb, txtEmail, txtUsername;
    private JPasswordField txtPassword;
    private ButtonGroup genderGroup;
    private static JComboBox<String> eventDropdown;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RegistrationForm frame = new RegistrationForm();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public RegistrationForm() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Downloads\\user.png"));
        setTitle("Event Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Make the frame full screen

        // Use GridBagLayout for centered alignment
        contentPane = new JPanel(new GridBagLayout());
        contentPane.setBackground(new Color(245, 245, 220)); // Beige background
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading Label
        JLabel headingLabel = new JLabel("User Registration Form");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 20, 5);
        contentPane.add(headingLabel, gbc);

        // Reset grid width and insets for the rest of the fields
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add form labels and fields
        gbc.gridx = 0;
        gbc.gridy++;
        contentPane.add(new JLabel("Name:"), gbc);
        txtName = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPane.add(new JLabel("Address:"), gbc);
        txtAddress = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtAddress, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPane.add(new JLabel("Gender:"), gbc);
        JPanel genderPanel = new JPanel(new FlowLayout());
        JRadioButton rbFemale = new JRadioButton("Female");
        JRadioButton rbMale = new JRadioButton("Male");
        genderPanel.add(rbFemale);
        genderPanel.add(rbMale);
        genderGroup = new ButtonGroup();
        genderGroup.add(rbFemale);
        genderGroup.add(rbMale);
        gbc.gridx = 1;
        contentPane.add(genderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPane.add(new JLabel("Age:"), gbc);
        txtAge = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtAge, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPane.add(new JLabel("Mobile No.:"), gbc);
        txtmb = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtmb, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPane.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPane.add(new JLabel("Select Event:"), gbc);
        eventDropdown = new JComboBox<>();
        gbc.gridx = 1;
        contentPane.add(eventDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPane.add(new JLabel("Username:"), gbc);
        txtUsername = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPane.add(new JLabel("Password:"), gbc);
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        contentPane.add(txtPassword, gbc);

        // Buttons in a separate panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(e -> {
            if (validateFields()) {
                registerUser(rbFemale, rbMale);
            }
        });
        buttonPanel.add(btnRegister);

        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(e -> resetFields());
        buttonPanel.add(btnReset);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            dispose(); // Close the current window
            new Login().setVisible(true); // Open Login page
        });
        buttonPanel.add(btnLogout);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        contentPane.add(buttonPanel, gbc);

        // Set initial visibility for specific buttons
        btnRegister.setVisible(true);
        btnReset.setVisible(true);

        loadEventsIntoDropdown(); // Load events into the eventDropdown
    }

    private boolean validateFields() {
        if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() ||
                txtAge.getText().isEmpty() || txtmb.getText().isEmpty() ||
                txtEmail.getText().isEmpty() || txtUsername.getText().isEmpty() ||
                txtPassword.getPassword().length == 0 || genderGroup.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(txtAge.getText());
            Long.parseLong(txtmb.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Age and Mobile", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static void loadEventsIntoDropdown() {
        try {
            Connection conn = createConnection();
            String sql = "SELECT event_name FROM event_details";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            eventDropdown.removeAllItems();
            while (resultSet.next()) {
                String eventName = resultSet.getString("event_name");
                eventDropdown.addItem(eventName);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerUser(JRadioButton rbFemale, JRadioButton rbMale) {
        try (Connection con = createConnection()) {
            String query = "INSERT INTO registration VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, txtName.getText());
            ps.setString(2, txtAddress.getText());
            ps.setString(3, rbFemale.isSelected() ? "Female" : "Male");
            ps.setInt(4, Integer.parseInt(txtAge.getText()));
            ps.setString(5, txtmb.getText());
            ps.setString(6, txtEmail.getText());
            ps.setString(7, txtUsername.getText());
            ps.setString(8, new String(txtPassword.getPassword()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registered successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadEventsIntoDropdown();
    }

    private void resetFields() {
        txtName.setText("");
        txtAddress.setText("");
        txtAge.setText("");
        txtmb.setText("");
        txtEmail.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        genderGroup.clearSelection();
    }

    public static Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/users", "root", "Abi");
    }
}

package swingex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel contentPane;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login frame = new Login();
            frame.setVisible(true);
        });
    }

    public Login() {
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen

        // Panel setup with beige background
        contentPane = new JPanel(new GridBagLayout());
        contentPane.setBackground(new Color(245, 245, 220)); // Beige background
        setContentPane(contentPane);

        // Setting layout constraints for centered alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome Label
        JLabel lblWelcome = new JLabel("Welcome to EventHub!");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        lblWelcome.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(lblWelcome, gbc);

        // Reset grid width for other elements
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Username Label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(lblUsername, gbc);

        // Username TextField in a white bordered panel
        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BorderLayout());
        usernamePanel.setBackground(Color.WHITE);
        usernamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        usernamePanel.add(txtUsername, BorderLayout.CENTER);
        gbc.gridx = 1;
        contentPane.add(usernamePanel, gbc);

        // Password Label
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(lblPassword, gbc);

        // Password Field in a white bordered panel
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BorderLayout());
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordPanel.add(txtPassword, BorderLayout.CENTER);
        gbc.gridx = 1;
        contentPane.add(passwordPanel, gbc);

        // Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(220, 220, 220));
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(btnLogin, gbc);

        // Login Button Action
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                if (username.equals("user") && password.equals("user")) {
                    dispose();
                    new RegistrationForm().setVisible(true);
                } else if (username.equals("admin") && password.equals("admin")) {
                    dispose();
                    new AdminPage().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}

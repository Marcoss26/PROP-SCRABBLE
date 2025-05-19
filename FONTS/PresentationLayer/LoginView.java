import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {

    public LoginView() {
        // Fondo gris claro
        setLayout(new GridBagLayout());
        setBackground(Color.decode("#F5F6FA")); // Gris claro

        // Panel principal de login
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(Color.decode("#F5F6FA")); // Gris claro
        loginPanel.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));
        loginPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título centrado
        JLabel loginTitle = new JLabel("Login");
        loginTitle.setFont(new Font("Dubai Medium", Font.BOLD, 48));
        loginTitle.setForeground(Color.decode("#2D2D2D"));
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo centrado
        JLabel loginSubtitle = new JLabel("Log in to a user profile");
        loginSubtitle.setFont(new Font("Dubai Medium", Font.PLAIN, 20));
        loginSubtitle.setForeground(Color.decode("#2D2D2D"));
        loginSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginSubtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // USERNAME
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 14));
        userLabel.setForeground(Color.decode("#2D2D2D"));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField();
        userField.setMaximumSize(new Dimension(350, 40));
        userField.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        userField.setBackground(Color.decode("#F7BBA9"));
        userField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        userField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // PASSWORD
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 14));
        passLabel.setForeground(Color.decode("#2D2D2D"));
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(350, 40));
        passField.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        passField.setBackground(Color.decode("#F7BBA9"));
        passField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botones centrados
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.decode("#F5F6FA")); // Gris claro

        JButton signUpBtn = new JButton("SIGN UP");
        signUpBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        signUpBtn.setBackground(Color.decode("#F7BBA9"));
        signUpBtn.setForeground(Color.decode("#2D2D2D"));

        JButton loginBtn = new JButton("LOG IN");
        loginBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        loginBtn.setBackground(Color.decode("#F7BBA9"));
        loginBtn.setForeground(Color.decode("#2D2D2D"));

        JButton cancelBtn = new JButton("CANCEL");
        cancelBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        cancelBtn.setBackground(Color.decode("#F7BBA9"));
        cancelBtn.setForeground(Color.decode("#2D2D2D"));

        buttonPanel.add(signUpBtn);
        buttonPanel.add(loginBtn);
        buttonPanel.add(cancelBtn);

        // Añadir componentes al panel principal
        loginPanel.add(loginTitle);
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(loginSubtitle);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(userLabel);
        loginPanel.add(userField);
        loginPanel.add(passLabel);
        loginPanel.add(passField);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(buttonPanel);

        // Centrar el panel de login en la pantalla
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginPanel, gbc);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 540);
        frame.setResizable(false);
        frame.add(new LoginView());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
package PresentationLayer;
import javax.swing.*;
import java.awt.*;

/**
 * SignupView representa la vista de registro de un nuevo usuario en el juego Scrabble.
 * Permite a los usuarios crear un nuevo perfil ingresando un nombre de usuario y una contraseña,
 * así como configurar si el perfil será público o privado.
 * @author Iván Alcubierre
 */
public class SignupView extends JPanel {

    /**
     * Constructor de la clase SignupView.
     * Inicializa el panel con un diseño y componentes para el registro de un nuevo usuario.
     */
    public SignupView() {
        // Fondo gris claro
        setLayout(new GridBagLayout());
        setBackground(Color.decode("#F5F6FA")); // Gris claro

        // Panel principal de signup
        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(new BoxLayout(signupPanel, BoxLayout.Y_AXIS));
        signupPanel.setBackground(Color.decode("#F5F6FA")); // Fondo igual que el fondo principal
        signupPanel.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));
        signupPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título
        JLabel signupTitle = new JLabel("Sign Up");
        signupTitle.setFont(new Font("Dubai Medium", Font.BOLD, 48));
        signupTitle.setForeground(Color.decode("#2D2D2D"));
        signupTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo
        JLabel signupSubtitle = new JLabel("Create a new profile");
        signupSubtitle.setFont(new Font("Dubai Medium", Font.PLAIN, 20));
        signupSubtitle.setForeground(Color.decode("#2D2D2D"));
        signupSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupSubtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // USERNAME
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 14));
        userLabel.setForeground(Color.decode("#2D2D2D"));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField();
        userField.setMaximumSize(new Dimension(350, 40));
        userField.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        userField.setBackground(Color.decode("#EDEDED"));
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
        passField.setBackground(Color.decode("#EDEDED"));
        passField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Public profile switch
        JPanel publicPanel = new JPanel();
        publicPanel.setLayout(new BoxLayout(publicPanel, BoxLayout.X_AXIS));
        publicPanel.setBackground(Color.decode("#F5F6FA"));
        publicPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel publicLabel = new JLabel("Public profile");
        publicLabel.setFont(new Font("Dubai Medium", Font.PLAIN, 14));
        publicLabel.setForeground(Color.decode("#2D2D2D"));

        // Simulación de un switch con un JCheckBox
        JCheckBox publicSwitch = new JCheckBox();
        publicSwitch.setOpaque(false);
        publicSwitch.setBackground(Color.decode("#F7BBA9"));
        publicSwitch.setFocusable(false);

        // Ajuste visual para simular el switch
        publicSwitch.setPreferredSize(new Dimension(40, 24));
        publicSwitch.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        publicPanel.add(publicLabel);
        publicPanel.add(Box.createHorizontalGlue());
        publicPanel.add(publicSwitch);

        // Botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        buttonPanel.setBackground(Color.decode("#F5F6FA"));

        JButton createBtn = new JButton("CREATE");
        createBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        createBtn.setBackground(Color.decode("#F7BBA9"));
        createBtn.setForeground(Color.decode("#2D2D2D"));

        JButton cancelBtn = new JButton("CANCEL");
        cancelBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        cancelBtn.setBackground(Color.decode("#F7BBA9"));
        cancelBtn.setForeground(Color.decode("#2D2D2D"));
        cancelBtn.setEnabled(false); // Para simular el botón deshabilitado visualmente

        buttonPanel.add(createBtn);
        buttonPanel.add(cancelBtn);

        // Añadir componentes al panel principal
        signupPanel.add(signupTitle);
        signupPanel.add(Box.createVerticalStrut(10));
        signupPanel.add(signupSubtitle);
        signupPanel.add(Box.createVerticalStrut(30));
        signupPanel.add(userLabel);
        signupPanel.add(userField);
        signupPanel.add(passLabel);
        signupPanel.add(passField);
        signupPanel.add(Box.createVerticalStrut(10));
        signupPanel.add(publicPanel);
        signupPanel.add(Box.createVerticalStrut(20));
        signupPanel.add(buttonPanel);

        // Centrar el panel de signup en la pantalla
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(signupPanel, gbc);
    }

    /**
     * Método principal para ejecutar la vista de registro.
     * Crea un JFrame y añade una instancia de SignupView.
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Signup View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 540);
        frame.setResizable(false);
        frame.add(new SignupView());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
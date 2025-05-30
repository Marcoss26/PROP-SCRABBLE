package PresentationLayer;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * LoginView representa la vista de inicio de sesión del juego.
 * Permite a los usuarios iniciar sesión o registrarse con un nombre de usuario y contraseña.
 * Incluye botones para iniciar sesión, registrarse y regresar al menú principal.
 * @author Iván Alcubierre
 */
public class LoginView extends JPanel {

    /**
     * Atributos de la clase LoginView.
     * @param cc Controlador de creación de perfiles.
     * @param userField Campo de texto para el nombre de usuario.
     * @param passField Campo de contraseña para la contraseña del usuario.
     * @param signUpBtn Botón para registrarse.
     * @param loginBtn Botón para iniciar sesión.
     * @param returnButton Botón para regresar al menú principal.
     */
    private CreationCtrl cc;
    private JTextField userField;
    private JPasswordField passField;
    private JButton signUpBtn;
    private JButton loginBtn;
    private JButton returnButton;

    /**
     * Constructor de la clase LoginView.
     * Inicializa el panel con un diseño y componentes para el inicio de sesión.
     */
    public LoginView() {

        cc = CreationCtrl.getInstance();
        // Fondo gris claro
        setLayout(new BorderLayout());
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

        userField = new JTextField();
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

        passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(350, 40));
        passField.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        passField.setBackground(Color.decode("#F7BBA9"));
        passField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botones centrados
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.decode("#F5F6FA")); // Gris claro

        signUpBtn = new JButton("SIGN UP");
        signUpBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        signUpBtn.setBackground(Color.decode("#F7BBA9"));
        signUpBtn.setForeground(Color.decode("#2D2D2D"));

        loginBtn = new JButton("LOG IN");
        loginBtn.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        loginBtn.setBackground(Color.decode("#F7BBA9"));
        loginBtn.setForeground(Color.decode("#2D2D2D"));

        //configuracion listeners de los botones

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if(!username.isEmpty() && !password.isEmpty()){
                cc.loginPlayer(username, password);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        signUpBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if(!username.isEmpty() && !password.isEmpty()){
                cc.createProfile(username, password);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(signUpBtn);
        buttonPanel.add(loginBtn);

        // Añadir componentes al panel principal
        loginPanel.add(Box.createVerticalGlue()); // Espacio flexible arriba
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
        loginPanel.add(Box.createVerticalGlue()); // Espacio flexible abajo

        add(loginPanel, BorderLayout.CENTER);

        // Botón Return en la parte inferior derecha
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.decode("#F5F6FA")); // Fondo del panel
        bottomPanel.setBorder(new EmptyBorder(8, 8, 8, 8)); // Añadir margen al panel inferior

        returnButton = new JButton("RETURN");
        returnButton.setFont(new Font("Dubai Medium", Font.PLAIN, 22)); // Tamaño de fuente ajustado a 22
        returnButton.setBackground(Color.decode("#F7BBA9")); // Fondo del botón
        returnButton.setFocusPainted(false);
        returnButton.setPreferredSize(new Dimension(120, 40)); // Tamaño consistente con otras vistas
        bottomPanel.add(returnButton); // Agregar el botón al panel inferior

        // Acción para el botón RETURN
        returnButton.addActionListener(e -> {
            PresentationCtrl.getInstance().showView("MainMenuView");
        });

        add(bottomPanel, BorderLayout.SOUTH); // Agregar el panel inferior al sur
    }

    /**
     * Obtiene el UserField donde el usuario ingresa su nombre de usuario.
     * @return el campo de texto para el nombre de usuario.
     */
    public JTextField getUserField() {
        return userField;
    }

    /**
     * Obtiene el PassField donde el usuario ingresa su contraseña.
     * @return el campo de contraseña.
     */
    public JPasswordField getPassField() {
        return passField;
    }

    /**
     * Obtiene el botón de registro.
     * @return el botón de registro.
     */
    public JButton getSignUpBtn() {
        return signUpBtn;
    }

    /**
     * Obtiene el botón de inicio de sesión.
     * @return el botón de inicio de sesión.
     */
    public JButton getLoginBtn() {
        return loginBtn;
    }

    /**
     * Obtiene el botón de retorno al menú principal.
     * @return el botón de retorno.
     */
    public JButton getReturnButton() {
        return returnButton;
    }

    /**
     * Limpia los campos de entrada de usuario y contraseña.
     */
    public void cleanFields() {
        userField.setText("");
        passField.setText("");
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo.
     * @param message El mensaje de error a mostrar.
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de éxito en un cuadro de diálogo.
     * @param message El mensaje de éxito a mostrar.
     */
    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
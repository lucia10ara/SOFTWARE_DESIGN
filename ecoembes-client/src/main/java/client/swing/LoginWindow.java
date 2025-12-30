package client.swing;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class LoginWindow extends JFrame {
    public LoginWindow(EcoembesController controller) {
        setTitle("Ecoembes - Portal de Acceso");
        
        Color greenEco = new Color(46, 139, 87);
        Color lightBack = new Color(248, 252, 248);

        getContentPane().setBackground(lightBack);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Insets para dar aire entre elementos
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título - Centrado y ocupando las dos columnas
        JLabel lblTitle = new JLabel("INICIO DE SESIÓN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setForeground(greenEco);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 20, 30, 20); 
        add(lblTitle, gbc);

        // Reset de insets para el cuerpo
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.gridwidth = 1;
        
        // EMAIL - Con barra larga
        gbc.gridx = 0; gbc.gridy = 1; 
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 12));
        add(lblEmail, gbc);
        
        // Aumentamos a 25 columnas para que la barra sea larga
        JTextField txtEmail = new JTextField(25); 
        gbc.gridx = 1; 
        gbc.weightx = 1.0; // Hace que el campo se estire horizontalmente
        add(txtEmail, gbc);

        // PASSWORD - Con barra larga
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("SansSerif", Font.BOLD, 12));
        add(lblPass, gbc);
        
        // Aumentamos a 25 columnas también
        JPasswordField txtPass = new JPasswordField(25);
        gbc.gridx = 1; 
        add(txtPass, gbc);

        // Botón de acceso
        JButton btnLogin = new JButton("ACCEDER AL SISTEMA");
        btnLogin.setBackground(greenEco);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnLogin.setPreferredSize(new Dimension(0, 40)); // Más alto
        btnLogin.setFocusPainted(false);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 10, 10, 10); 
        add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            if (controller.login(txtEmail.getText(), new String(txtPass.getPassword()))) {
                new MainWindow(controller).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error de autenticación", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
            }
        });

        // VENTANA MÁS ANCHA (550x380) para acomodar las barras largas
        setSize(450, 270); 
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
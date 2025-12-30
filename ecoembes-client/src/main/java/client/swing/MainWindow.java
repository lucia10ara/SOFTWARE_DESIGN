package client.swing;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;



public class MainWindow extends JFrame {
    public MainWindow(EcoembesController controller) {
        setTitle("Ecoembes - Gestión de Contenedores");
        setSize(600, 450);
        setLayout(new BorderLayout());
        
        //para q ocupe casi td la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width - 300, screenSize.height - 300);

        Color greenEco = new Color(46, 139, 87);

        // CABECERA (Header)
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(greenEco);
        pnlHeader.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblInfo = new JLabel("Empleado conectado | Token: " + controller.getToken() + "...");
        lblInfo.setForeground(Color.WHITE);
        lblInfo.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setFocusPainted(false);
        
        pnlHeader.add(lblInfo, BorderLayout.WEST);
        pnlHeader.add(btnLogout, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // CUERPO (Acciones Persona B)
        JPanel pnlContent = new JPanel(new GridBagLayout());
        pnlContent.setBackground(Color.WHITE);
        
        JButton btnCreate = new JButton("<html><center>➕<br>Crear Nuevo Contenedor</center></html>");
        btnCreate.setPreferredSize(new Dimension(220, 120));
        btnCreate.setBackground(new Color(230, 245, 230));
        btnCreate.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCreate.setForeground(greenEco);

        pnlContent.add(btnCreate);
        add(pnlContent, BorderLayout.CENTER);

        // EVENTOS
        btnLogout.addActionListener(e -> {
            controller.logout();
            new LoginWindow(controller).setVisible(true);
            dispose();
        });

        btnCreate.addActionListener(e -> {
            // Aquí irá la lógica de la Persona B
            JOptionPane.showMessageDialog(this, "Abriendo formulario de nuevo contenedor...");
        });

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

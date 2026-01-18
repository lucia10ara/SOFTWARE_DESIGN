package client.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow(EcoembesController controller) {
        setTitle("Ecoembes - Gestión de Contenedores");
        setSize(600, 450);
        setLayout(new BorderLayout());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width - 300, screenSize.height - 300);

        Color greenEco = new Color(46, 139, 87);

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

        JPanel pnlContent = new JPanel(new GridBagLayout());
        pnlContent.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridy = 0;

        JButton btnCreate = new JButton("<html><center>➕<br>Crear Nuevo Contenedor</center></html>");
        btnCreate.setPreferredSize(new Dimension(220, 120));
        btnCreate.setBackground(new Color(230, 245, 230));
        btnCreate.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCreate.setForeground(greenEco);

        JButton btnList = new JButton("<html><center>📋<br>Listar Contenedores</center></html>");
        btnList.setPreferredSize(new Dimension(220, 120));
        btnList.setBackground(new Color(230, 245, 230));
        btnList.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnList.setForeground(greenEco);

        gbc.gridx = 0;
        pnlContent.add(btnCreate, gbc);

        gbc.gridx = 1;
        pnlContent.add(btnList, gbc);

        add(pnlContent, BorderLayout.CENTER);

        
        JButton btnAssign = new JButton("<html><center>🔄<br>Asignar Dumpster</center></html>");
        btnAssign.setPreferredSize(new Dimension(220, 120));
        btnAssign.setBackground(new Color(230, 245, 230));
        btnAssign.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnAssign.setForeground(greenEco);

        gbc.gridy = 1;
        gbc.gridx = 0;
        pnlContent.add(btnAssign, gbc);

        btnAssign.addActionListener(e -> new AssignDumpsterWindow(controller).setVisible(true));

        
        btnLogout.addActionListener(e -> {
            controller.logout();
            new LoginWindow(controller).setVisible(true);
            dispose();
        });

        btnCreate.addActionListener(e -> new CreateDumpsterWindow(controller).setVisible(true));
        btnList.addActionListener(e -> new ListDumpstersWindow(controller).setVisible(true));

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

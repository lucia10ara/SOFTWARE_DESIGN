package client.swing;

import client.data.DumpsterDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
listar dumpsters (GET /dumpsters) y mostrarlos en tabla.
 */
public class ListDumpstersWindow extends JFrame {

    private final EcoembesController controller;
    private final DefaultTableModel model;

    public ListDumpstersWindow(EcoembesController controller) {
        this.controller = controller;

        setTitle("Listar Dumpsters");
        setSize(900, 450);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lbl = new JLabel("Dumpsters registrados");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 14f));
        top.add(lbl, BorderLayout.WEST);

        JButton btnRefresh = new JButton("Refrescar");
        top.add(btnRefresh, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new Object[]{
                        "ID", "Address", "Postal", "City", "Country",
                        "InitialCap", "#Containers", "FillLevel", "Status"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnRefresh.addActionListener(e -> loadDumpsters());

        loadDumpsters();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void loadDumpsters() {
        try {
            List<DumpsterDTO> list = controller.listDumpsters();
            model.setRowCount(0);
            for (DumpsterDTO d : list) {
                model.addRow(new Object[]{
                        d.getDumpsterId(),
                        safe(d.getAddress()),
                        d.getPostalCode(),
                        safe(d.getCity()),
                        safe(d.getCountry()),
                        d.getInitialCapacity(),
                        d.getNumContainers(),
                        d.getCurrentFillLevel(),
                        safe(d.getCurrentStatus())
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error listando dumpsters: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}

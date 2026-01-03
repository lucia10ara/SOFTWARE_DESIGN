package client.swing;

import client.data.DumpsterDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Comparator;

/**
 * Persona B: crear dumpster (POST /dumpsters)
 * Campos: Location, Postal Code, Initial Capacity.
 */
public class CreateDumpsterWindow extends JFrame {

    private final EcoembesController controller;

    private final JTextField txtLocation = new JTextField();
    private final JSpinner spPostalCode = new JSpinner(new SpinnerNumberModel(48000, 0, 99999, 1));
    private final JSpinner spInitialCapacity = new JSpinner(new SpinnerNumberModel(100, 0, 1_000_000, 1));

    public CreateDumpsterWindow(EcoembesController controller) {
        this.controller = controller;

        setTitle("Crear Dumpster");
        setSize(520, 320);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Location (Address):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        form.add(txtLocation, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        form.add(new JLabel("Postal Code:"), gbc);
        gbc.gridx = 1;
        form.add(spPostalCode, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        form.add(new JLabel("Initial Capacity:"), gbc);
        gbc.gridx = 1;
        form.add(spInitialCapacity, gbc);

        add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCreate = new JButton("Crear");
        JButton btnCancel = new JButton("Cancelar");
        actions.add(btnCancel);
        actions.add(btnCreate);
        add(actions, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> dispose());
        btnCreate.addActionListener(e -> onCreate());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void onCreate() {
        String location = txtLocation.getText() == null ? "" : txtLocation.getText().trim();
        int postalCode = (Integer) spPostalCode.getValue();
        int initialCapacity = (Integer) spInitialCapacity.getValue();

        if (location.isBlank()) {
            JOptionPane.showMessageDialog(this, "La ubicación no puede estar vacía.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // El servidor actual NO autogenera dumpster_id, así que lo calculamos como (max + 1).
            int nextId = controller.listDumpsters().stream()
                    .max(Comparator.comparingInt(DumpsterDTO::getDumpsterId))
                    .map(d -> d.getDumpsterId() + 1)
                    .orElse(1);

            DumpsterDTO dto = new DumpsterDTO();
            dto.setDumpsterId(nextId);
            dto.setAddress(location);
            dto.setPostalCode(postalCode);
            dto.setInitialCapacity(initialCapacity);

            // Valores por defecto (no están en el formulario del enunciado)
            dto.setCity("-");
            dto.setCountry("-");
            dto.setNumContainers(1);

            DumpsterDTO created = controller.createDumpster(dto);
            int createdId = created != null ? created.getDumpsterId() : nextId;

            JOptionPane.showMessageDialog(
                    this,
                    "Dumpster creado correctamente (ID: " + createdId + ")",
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error creando dumpster: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

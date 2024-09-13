package com.mac.doctor.infrastructure.adapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.mac.doctor.application.CreateDoctorUC;
import com.mac.doctor.application.DeleteDoctorUC;
import com.mac.doctor.application.FindAllDoctorUC;
import com.mac.doctor.application.FindDoctorUC;
import com.mac.doctor.application.UpdateDoctorUC;
import com.mac.doctor.domain.service.DoctorService;
import com.mac.doctor.infrastructure.repository.DoctorRepository;
import com.mac.doctor.domain.entity.Doctor;
import com.mac.doctor.infrastructure.adapter.DoctorAdapter;
import com.mac.ui.OptionsController;

public class DoctorAdapter extends JFrame {
    private final DoctorService doctorService;
    private final CreateDoctorUC createDoctorUC;
    private final UpdateDoctorUC updateDoctorUC;
    private final DeleteDoctorUC deleteDoctorUC;
    private final FindDoctorUC findDoctorUC;
    private final FindAllDoctorUC findAllDoctorUC;

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private OptionsController optionsController;

    public DoctorAdapter() {
        this.doctorService = new DoctorRepository();
        this.createDoctorUC = new CreateDoctorUC(doctorService);
        this.updateDoctorUC = new UpdateDoctorUC(doctorService);
        this.deleteDoctorUC = new DeleteDoctorUC(doctorService);
        this.findDoctorUC = new FindDoctorUC(doctorService);
        this.findAllDoctorUC = new FindAllDoctorUC(doctorService);
        this.optionsController = new OptionsController();

        setTitle("Doctor Menu");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Panel principal con los botones del menú
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, "Menu");

        // Paneles para cada operación
        JPanel addPanel = createOperationPanel("Add Doctor", "Add", createAddPanel());
        JPanel searchPanel = createOperationPanel("Search Doctor", "Search", createSearchPanel());
        JPanel findAllPanel = createOperationPanel("Find All Doctor", "Find All", createFindAllPanel());
        JPanel updatePanel = createOperationPanel("Update Doctor", "Update", createUpdatePanel());
        JPanel deletePanel = createOperationPanel("Delete Doctor", "Delete", createDeletePanel());

        // Añadir los paneles al CardLayout
        mainPanel.add(addPanel, "Add");
        mainPanel.add(searchPanel, "Search");
        mainPanel.add(findAllPanel, "FindAll");
        mainPanel.add(updatePanel, "Update");
        mainPanel.add(deletePanel, "Delete");

        // Añadir el panel principal al JFrame
        add(mainPanel);

        // Mostrar el menú inicial
        cardLayout.show(mainPanel, "Menu");

        // Hacer visible la ventana
        setVisible(true);
    }

    private JPanel createHeaderPanel(String title) {
        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Añadir espacio debajo del encabezado
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Crear encabezado solo para el menú
        JPanel headerPanel = createHeaderPanel("Doctor CRUD");
        panel.add(headerPanel, BorderLayout.NORTH);

        // Crear un panel para los botones con GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));

        // Añadir márgenes alrededor de los botones
        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Márgenes laterales

        // Crear botones personalizados con esquinas redondeadas
        JButton addButton = createRoundedButton("Add Doctor");
        JButton searchButton = createRoundedButton("Search Doctor");
        JButton findAllButton = createRoundedButton("Find All Doctor");
        JButton updateButton = createRoundedButton("Update Doctor");
        JButton deleteButton = createRoundedButton("Delete Doctor");
        JButton exitButton = createRoundedButton("Back");

        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(findAllButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exitButton);

        marginPanel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(marginPanel, BorderLayout.CENTER);

        // Action Listeners para cada botón
        addButton.addActionListener(e -> cardLayout.show(mainPanel, "Add"));
        searchButton.addActionListener(e -> cardLayout.show(mainPanel, "Search"));
        findAllButton.addActionListener(e -> cardLayout.show(mainPanel, "FindAll"));
        updateButton.addActionListener(e -> cardLayout.show(mainPanel, "Update"));
        deleteButton.addActionListener(e -> cardLayout.show(mainPanel, "Delete"));
        exitButton.addActionListener(e -> {
            this.dispose();
            optionsController.setVisible(true);
        });

        return panel;
    }

    private JPanel createOperationPanel(String title, String cardName, JPanel operationPanel) {
        JPanel panel = new JPanel(new BorderLayout());

        // Crear encabezado solo para operaciones
        JPanel headerPanel = createHeaderPanel(title);
        panel.add(headerPanel, BorderLayout.NORTH);

        panel.add(operationPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getForeground());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }

            @Override
            public void setContentAreaFilled(boolean b) {
                super.setContentAreaFilled(false);
            }
        };
        button.setFocusPainted(false);
        return button;
    }

    private JPanel createAddPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));

        JLabel nameLabel = new JLabel("Enter Doctor name:");
        JTextField nameField = new JTextField(10);
        JLabel specialtyIdLabel = new JLabel("Enter Doctor specialtyId:");
        JTextField specialtyIdField = new JTextField(10);
        JLabel startTimeLabel = new JLabel("Enter Doctor startTime (yyyy-MM-dd HH:mm):");
        JTextField startTimeField = new JTextField(10);
        JLabel endTimeLabel = new JLabel("Enter Doctor endTime (yyyy-MM-dd HH:mm):");
        JTextField endTimeField = new JTextField(10);
        JButton submitButton = createRoundedButton("Submit");
        JButton backButton = createRoundedButton("Back");

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(specialtyIdLabel);
        formPanel.add(specialtyIdField);
        formPanel.add(startTimeLabel);
        formPanel.add(startTimeField);
        formPanel.add(endTimeLabel);
        formPanel.add(endTimeField);
        formPanel.add(submitButton);
        formPanel.add(backButton);

        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Márgenes laterales
        marginPanel.add(formPanel, BorderLayout.CENTER);

        panel.add(marginPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            int specialtyId = Integer.parseInt(specialtyIdField.getText().trim());
            String startTime = startTimeField.getText().trim();
            String endTime = endTimeField.getText().trim();

            // Validar si los campos están vacíos
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Doctor name cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (specialtyIdField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Doctor specialtyId cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (startTime.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Doctor startTime cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (endTime.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Doctor endTime cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Valido el formato de startTime y endTime usando SimpleDateFormat
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dateFormat.setLenient(false);

            try {
                dateFormat.parse(startTime);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid startTime format. Use yyyy-MM-dd HH:mm.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                dateFormat.parse(endTime);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid endTime format. Use yyyy-MM-dd HH:mm.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear nuevo Doctor
            Doctor doctor = new Doctor();
            doctor.setName(name);
            doctor.setSpecialtyId(specialtyId);
            doctor.setStartTime(startTime);
            doctor.setEndTime(endTime);

            // Ejecutar caso de uso
            createDoctorUC.execute(doctor);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, "Doctor added successfully.");

            // Limpiar los campos de texto
            nameField.setText("");
            specialtyIdField.setText("");
            startTimeField.setText("");
            endTimeField.setText("");
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));

        JLabel idLabel = new JLabel("Enter Doctor ID:");

        JTextField idField = new JTextField(5);
        idField.setPreferredSize(new Dimension(300, 250)); // Ajusta la altura del campo de texto

        JButton submitButton = createRoundedButton("Search");
        submitButton.setPreferredSize(new Dimension(100, 30)); // Ajusta la altura del botón

        JButton backButton = createRoundedButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30)); // Ajusta la altura del botón

        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(submitButton);
        formPanel.add(backButton);

        // Añadir márgenes alrededor del formulario
        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Márgenes laterales
        marginPanel.add(formPanel, BorderLayout.CENTER);

        panel.add(marginPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                findDoctorUC.execute(id).ifPresentOrElse(
                        doctor -> showDoctorDetails(doctor),
                        () -> JOptionPane.showMessageDialog(this, "Doctor not found.", "Error",
                                JOptionPane.ERROR_MESSAGE));
                idField.setText(""); // Limpiar el campo de texto
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    private JPanel createFindAllPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Crear encabezado del panel
        JPanel headerPanel = createHeaderPanel("Doctor");

        // Crear tabla para mostrar los datos
        String[] columnNames = { "ID", "Name", "SpecialtyId", "StartTime", "EndTime" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0); // Modelo de la tabla
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Añadir la tabla al panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = createRoundedButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        JButton searchButton = createRoundedButton("Search");
        searchButton.addActionListener(e -> {
            // Limpiar el modelo de la tabla antes de agregar nuevos datos
            tableModel.setRowCount(0);

            // Obtiene todos los doctoros y los añade a la tabla
            findAllDoctorUC.execute().forEach(doctor -> {
                Object[] row = { doctor.getId(), doctor.getName(), doctor.getStartTime(), doctor.getEndTime() };
                tableModel.addRow(row);
            });
        });

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(searchButton);

        // Añadir el panel del botón debajo de la tabla
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createUpdatePanel() {
        JPanel panel = new JPanel(new BorderLayout());
    
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10)); 
        JLabel idLabel = new JLabel("Enter Doctor ID:");
        JTextField idField = new JTextField(20);
        JLabel nameLabel = new JLabel("Enter new Doctor name:");
        JTextField nameField = new JTextField(10);
        JLabel specialtyIdLabel = new JLabel("Enter new Doctor specialtyId:");
        JTextField specialtyIdField = new JTextField(10);
        JLabel startTimeLabel = new JLabel("Enter new Doctor startTime (yyyy-MM-dd HH:mm):");
        JTextField startTimeField = new JTextField(10);
        JLabel endTimeLabel = new JLabel("Enter new Doctor endTime (yyyy-MM-dd HH:mm):");
        JTextField endTimeField = new JTextField(10);
        JButton submitButton = createRoundedButton("Submit");
        JButton backButton = createRoundedButton("Back");
    
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(specialtyIdLabel);
        formPanel.add(specialtyIdField);
        formPanel.add(startTimeLabel);
        formPanel.add(startTimeField);
        formPanel.add(endTimeLabel);
        formPanel.add(endTimeField);
        formPanel.add(submitButton);
        formPanel.add(backButton);
    
        // Añadir márgenes alrededor del formulario
        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Márgenes
        marginPanel.add(formPanel, BorderLayout.CENTER);
    
        panel.add(marginPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                int specialtyId = Integer.parseInt(specialtyIdField.getText().trim());
                String startTime = startTimeField.getText().trim();
                String endTime = endTimeField.getText().trim();
    
                // Validar si los campos están vacíos
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Doctor name cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (startTime.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Doctor startTime cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (endTime.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Doctor endTime cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                // Valido el formato de startTime y endTime usando SimpleDateFormat
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                dateFormat.setLenient(false);
    
                try {
                    dateFormat.parse(startTime);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(panel, "Invalid startTime format. Use yyyy-MM-dd HH:mm.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                try {
                    dateFormat.parse(endTime);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(panel, "Invalid endTime format. Use yyyy-MM-dd HH:mm.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                // Buscar doctor y actualizar si es encontrado
                findDoctorUC.execute(id).ifPresentOrElse(
                        doctor -> {
                            doctor.setName(name);
                            doctor.setSpecialtyId(specialtyId);
                            doctor.setStartTime(startTime);
                            doctor.setEndTime(endTime);
    
                            updateDoctorUC.execute(doctor);
                            JOptionPane.showMessageDialog(panel, "Doctor updated successfully.");
    
                            // Limpiar los campos
                            idField.setText("");
                            nameField.setText("");
                            specialtyIdField.setText("");
                            startTimeField.setText("");
                            endTimeField.setText("");
                        },
                        () -> JOptionPane.showMessageDialog(panel, "Doctor not found.", "Error",
                                JOptionPane.ERROR_MESSAGE));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input. Please enter valid integers.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    
        // Acción del botón "Back"
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
    
        return panel;
    }
    

    private JPanel createDeletePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));

        JLabel idLabel = new JLabel("Enter Doctor ID:");
        JTextField idField = new JTextField(20);
        JButton submitButton = createRoundedButton("Delete");
        JButton backButton = createRoundedButton("Back");

        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(submitButton);
        formPanel.add(backButton);

        // Añadir márgenes alrededor del formulario
        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Márgenes laterales
        marginPanel.add(formPanel, BorderLayout.CENTER);

        panel.add(marginPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this Doctor?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    deleteDoctorUC.execute(id);
                    JOptionPane.showMessageDialog(this, "Doctor deleted successfully.");
                    idField.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    private void showDoctorDetails(Doctor Doctor) {
        String details = String.format("""
                Doctor found:
                ID: %d
                Name: %s
                SpecialtyId: %d
                StartTime: %s
                EndTime: %s
                """, Doctor.getId(), Doctor.getName(), Doctor.getSpecialtyId(), Doctor.getStartTime(),
                Doctor.getEndTime());
        JOptionPane.showMessageDialog(this, details, "Doctor Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new DoctorAdapter();
    }

}

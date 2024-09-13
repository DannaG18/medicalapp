package com.mac.appointment.infrastructure.adapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.mac.appointment.application.CreateAppointmentUC;
import com.mac.appointment.application.DeleteAppointmentUC;
import com.mac.appointment.application.FindAllAppointmentUC;
import com.mac.appointment.application.FindAppointmentUC;
import com.mac.appointment.application.UpdateAppointmentUC;
import com.mac.appointment.domain.entity.Appointment;
import com.mac.appointment.domain.service.AppointmentService;
import com.mac.appointment.infrastructure.repository.AppointmentRepository;
import com.mac.ui.OptionsController;

public class AppointmentAdapter extends JFrame {
    private final AppointmentService appointmentService;
    private final CreateAppointmentUC createAppointmentUC;
    private final UpdateAppointmentUC updateAppointmentUC;
    private final DeleteAppointmentUC deleteAppointmentUC;
    private final FindAllAppointmentUC findAllAppointmentUC;
    private final FindAppointmentUC findAppointmentUC;

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private OptionsController optionsController;

    public AppointmentAdapter() {
        this.appointmentService = new AppointmentRepository();
        this.createAppointmentUC = new CreateAppointmentUC(appointmentService);
        this.updateAppointmentUC = new UpdateAppointmentUC(appointmentService);
        this.deleteAppointmentUC = new DeleteAppointmentUC(appointmentService);
        this.findAllAppointmentUC = new FindAllAppointmentUC(appointmentService);
        this.findAppointmentUC = new FindAppointmentUC(appointmentService);

        setTitle("Appointment Menu");
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
        JPanel addPanel = createOperationPanel("Add Appointment", "Add", createAddPanel());
        JPanel searchPanel = createOperationPanel("Search Appointment", "Search", createSearchPanel());
        JPanel findAllPanel = createOperationPanel("Find All Appointment", "Find All", createFindAllPanel());
        JPanel updatePanel = createOperationPanel("Update Appointment", "Update", createUpdatePanel());
        JPanel deletePanel = createOperationPanel("Delete Appointment", "Delete", createDeletePanel());

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
        JPanel headerPanel = createHeaderPanel("Appointment CRUD");
        panel.add(headerPanel, BorderLayout.NORTH);

        // Crear un panel para los botones con GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));

        // Añadir márgenes alrededor de los botones
        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Márgenes laterales

        // Crear botones personalizados con esquinas redondeadas
        JButton addButton = createRoundedButton("Add Appointment");
        JButton searchButton = createRoundedButton("Search Appointment");
        JButton findAllButton = createRoundedButton("Find All Appointment");
        JButton updateButton = createRoundedButton("Update Appointment");
        JButton deleteButton = createRoundedButton("Delete Appointment");
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

    private JPanel createOperationPanel(String title, String cardPatientId, JPanel operationPanel) {
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

        JLabel doctorIdLabel = new JLabel("Enter Appointment doctorId:");
        JTextField doctorIdField = new JTextField(10);
        JLabel patientIdLabel = new JLabel("Enter Appointment patientId:");
        JTextField patientIdField = new JTextField(10);
        JLabel dateTimeLabel = new JLabel("Enter Appointment dateTime (yyyy-MM-dd HH:mm):");
        JTextField dateTimeField = new JTextField(10);
        JLabel statusLabel = new JLabel("Enter Appointment status:");
        JTextField statusField = new JTextField(10);
        JButton submitButton = createRoundedButton("Submit");
        JButton backButton = createRoundedButton("Back");

        formPanel.add(doctorIdLabel);
        formPanel.add(doctorIdField);
        formPanel.add(patientIdLabel);
        formPanel.add(patientIdField);
        formPanel.add(dateTimeLabel);
        formPanel.add(dateTimeField);
        formPanel.add(statusLabel);
        formPanel.add(statusField);
        formPanel.add(submitButton);
        formPanel.add(backButton);

        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Márgenes laterales
        marginPanel.add(formPanel, BorderLayout.CENTER);

        panel.add(marginPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> {
            int doctorId = Integer.parseInt(doctorIdField.getText().trim());
            int patientId = Integer.parseInt(patientIdField.getText().trim());
            String dateTime = dateTimeField.getText().trim();
            String status = statusField.getText().trim();

            // Validar si los campos están vacíos
            if (patientIdField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Appointment patientId cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (doctorIdField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Appointment doctorId cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (dateTime.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Appointment dateTime cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (status.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Appointment status cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Valido el formato de dateTime y status usando SimpleDateFormat
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dateFormat.setLenient(false);

            try {
                dateFormat.parse(dateTime);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid dateTime format. Use yyyy-MM-dd HH:mm.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                dateFormat.parse(status);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid status format. Use yyyy-MM-dd HH:mm.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear nuevo Appointment
            Appointment appointment = new Appointment();
            appointment.setPatientId(patientId);
            appointment.setDoctorId(doctorId);
            appointment.setDateTime(dateTime);
            appointment.setStatus(status);

            // Ejecutar caso de uso
            createAppointmentUC.execute(appointment);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, "Appointment added successfully.");

            // Limpiar los campos de texto
            patientIdField.setText("");
            doctorIdField.setText("");
            dateTimeField.setText("");
            statusField.setText("");
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));

        JLabel idLabel = new JLabel("Enter Appointment ID:");

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
                findAppointmentUC.execute(id).ifPresentOrElse(
                        appointment -> showAppointmentDetails(appointment),
                        () -> JOptionPane.showMessageDialog(this, "Appointment not found.", "Error",
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
        JPanel headerPanel = createHeaderPanel("Appointment");

        // Crear tabla para mostrar los datos
        String[] columnPatientIds = { "ID", "PatientId", "DoctorId", "DateTime", "Status" };
        DefaultTableModel tableModel = new DefaultTableModel(columnPatientIds, 0); // Modelo de la tabla
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

            // Obtiene todos los appointmentos y los añade a la tabla
            findAllAppointmentUC.execute().forEach(appointment -> {
                Object[] row = { appointment.getId(), appointment.getPatientId(), appointment.getDateTime(), appointment.getStatus() };
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
        JLabel idLabel = new JLabel("Enter Appointment ID:");
        JTextField idField = new JTextField(20);
        JLabel doctorIdLabel = new JLabel("Enter Appointment doctorId:");
        JTextField doctorIdField = new JTextField(10);
        JLabel patientIdLabel = new JLabel("Enter Appointment patientId:");
        JTextField patientIdField = new JTextField(10);
        JLabel dateTimeLabel = new JLabel("Enter Appointment dateTime (yyyy-MM-dd HH:mm):");
        JTextField dateTimeField = new JTextField(10);
        JLabel statusLabel = new JLabel("Enter Appointment status:");
        JTextField statusField = new JTextField(10);
        JButton submitButton = createRoundedButton("Submit");
        JButton backButton = createRoundedButton("Back");
    
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(doctorIdLabel);
        formPanel.add(doctorIdField);
        formPanel.add(patientIdLabel);
        formPanel.add(patientIdField);
        formPanel.add(dateTimeLabel);
        formPanel.add(dateTimeField);
        formPanel.add(statusLabel);
        formPanel.add(statusField);
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
                int doctorId = Integer.parseInt(doctorIdField.getText().trim());
                int patientId = Integer.parseInt(patientIdField.getText().trim());
                String dateTime = dateTimeField.getText().trim();
                String status = statusField.getText().trim();
    
                // Validar si los campos están vacíos
                if (patientIdField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Appointment patientId cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (doctorIdField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Appointment doctorId cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (dateTime.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Appointment dateTime cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (status.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Appointment status cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Valido el formato de dateTime y status usando SimpleDateFormat
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                dateFormat.setLenient(false);
    
                try {
                    dateFormat.parse(dateTime);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(panel, "Invalid dateTime format. Use yyyy-MM-dd HH:mm.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                try {
                    dateFormat.parse(status);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(panel, "Invalid status format. Use yyyy-MM-dd HH:mm.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                // Buscar appointment y actualizar si es encontrado
                findAppointmentUC.execute(id).ifPresentOrElse(
                        appointment -> {
                            appointment.setPatientId(patientId);
                            appointment.setDoctorId(doctorId);
                            appointment.setDateTime(dateTime);
                            appointment.setStatus(status);
    
                            updateAppointmentUC.execute(appointment);
                            JOptionPane.showMessageDialog(panel, "Appointment updated successfully.");
    
                            // Limpiar los campos
                            idField.setText("");
                            patientIdField.setText("");
                            doctorIdField.setText("");
                            dateTimeField.setText("");
                            statusField.setText("");
                        },
                        () -> JOptionPane.showMessageDialog(panel, "Appointment not found.", "Error",
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

        JLabel idLabel = new JLabel("Enter Appointment ID:");
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
                int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this Appointment?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    deleteAppointmentUC.execute(id);
                    JOptionPane.showMessageDialog(this, "Appointment deleted successfully.");
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

    private void showAppointmentDetails(Appointment Appointment) {
        String details = String.format("""
                Appointment found:
                ID: %d
                PatientId: %d
                DoctorId: %d
                DateTime: %s
                Status: %s
                """, Appointment.getId(), Appointment.getPatientId(), Appointment.getDoctorId(), Appointment.getDateTime(),
                Appointment.getStatus());
        JOptionPane.showMessageDialog(this, details, "Appointment Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new AppointmentAdapter();
    }

}


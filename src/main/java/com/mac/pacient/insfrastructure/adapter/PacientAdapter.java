package com.mac.pacient.insfrastructure.adapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import com.mac.pacient.application.CreatePacientUC;
import com.mac.pacient.application.DeletePacientUC;
import com.mac.pacient.application.FindAllPacientUC;
import com.mac.pacient.application.FindPacientUC;
import com.mac.pacient.application.UpdatePacientUC;
import com.mac.pacient.domain.entity.Pacient;
import com.mac.pacient.domain.service.PacientService;
import com.mac.pacient.insfrastructure.repository.PacientRepository;
import com.mac.ui.OptionsController;

public class PacientAdapter extends JFrame{
    private final PacientService pacientService;
    private final CreatePacientUC createPacientUC;
    private final UpdatePacientUC updatePacientUC;
    private final DeletePacientUC deletePacientUC;
    private final FindPacientUC findPacientUC;
    private final FindAllPacientUC findAllPacientUC;

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private OptionsController optionsController;

    public PacientAdapter() {
        this.pacientService = new PacientRepository();
        this.createPacientUC = new CreatePacientUC(pacientService);
        this.updatePacientUC = new UpdatePacientUC(pacientService);
        this.deletePacientUC = new DeletePacientUC(pacientService);
        this.findPacientUC = new FindPacientUC(pacientService);
        this.findAllPacientUC = new FindAllPacientUC(pacientService);

        setTitle("Pacient Menu");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Panel principal con los botones del menú
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, "Menu");

        // Paneles para cada operación
        JPanel addPanel = createOperationPanel("Add Pacient", "Add", createAddPanel());
        JPanel searchPanel = createOperationPanel("Search Pacient", "Search", createSearchPanel());
        JPanel findAllPanel = createOperationPanel("Find All Pacient", "Find All", createFindAllPanel());
        JPanel updatePanel = createOperationPanel("Update Pacient", "Update", createUpdatePanel());
        JPanel deletePanel = createOperationPanel("Delete Pacient", "Delete", createDeletePanel());

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
        JPanel headerPanel = createHeaderPanel("Pacient CRUD");
        panel.add(headerPanel, BorderLayout.NORTH);

        // Crear un panel para los botones con GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));

        // Añadir márgenes alrededor de los botones
        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Márgenes laterales

        // Crear botones personalizados con esquinas redondeadas
        JButton addButton = createRoundedButton("Add Pacient");
        JButton searchButton = createRoundedButton("Search Pacient");
        JButton findAllButton = createRoundedButton("Find All Pacient");
        JButton updateButton = createRoundedButton("Update Pacient");
        JButton deleteButton = createRoundedButton("Delete Pacient");
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

        JPanel formPanel = new JPanel(new GridLayout(8, 1, 10, 10));

        JLabel nameLabel = new JLabel("Enter Pacient name:");
        JTextField nameField = new JTextField(10);
        JLabel lastNameLabel = new JLabel("Enter Pacient lastName: ");
        JTextField lastNameField = new JTextField(10);
        JLabel birthDateLabel = new JLabel("Enter Pacient birthDate:");
        JTextField birthDateField = new JTextField(10);
        JLabel addressLabel = new JLabel("Enter Pacient address:");
        JTextField addressField = new JTextField(10);
        JLabel phoneLabel = new JLabel("Enter Pacient phone:");
        JTextField phoneField = new JTextField(10);
        JLabel emailLabel = new JLabel("Enter Pacient email:");
        JTextField emailField = new JTextField(10);
        JButton submitButton = createRoundedButton("Submit");
        JButton backButton = createRoundedButton("Back");

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        formPanel.add(birthDateLabel);
        formPanel.add(birthDateField);
        formPanel.add(addressLabel);
        formPanel.add(addressField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(submitButton);
        formPanel.add(backButton);

        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Márgenes laterales
        marginPanel.add(formPanel, BorderLayout.CENTER);

        panel.add(marginPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String birthDate = birthDateField.getText().trim();
            String address = addressField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();

            // Validar si los campos están vacíos
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pacient name cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (lastName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pacient lastName cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (birthDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pacient birthDate cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pacient address cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pacient phone cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pacient email cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear nuevo Pacient
            Pacient pacient = new Pacient();
            pacient.setName(name);
            pacient.setLastName(lastName);
            pacient.setBirthDate(birthDate);
            pacient.setAddress(address);
            pacient.setPhone(phone);
            pacient.setEmail(email);

            // Ejecutar caso de uso
            createPacientUC.execute(pacient);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, "Pacient added successfully.");

            // Limpiar los campos de texto
            nameField.setText("");
            lastNameField.setText("");
            birthDateField.setText("");
            addressField.setText("");
            phoneField.setText("");
            emailField.setText("");
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));

        JLabel idLabel = new JLabel("Enter Pacient ID:");

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
                findPacientUC.execute(id).ifPresentOrElse(
                        pacient -> showPacientDetails(pacient),
                        () -> JOptionPane.showMessageDialog(this, "Pacient not found.", "Error",
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
        JPanel headerPanel = createHeaderPanel("Pacient");

        // Crear tabla para mostrar los datos
        String[] columnNames = { "ID", "Name", "SpecialtyId", "LastName", "BirthDate" };
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

            // Obtiene todos los pacientos y los añade a la tabla
            findAllPacientUC.execute().forEach(pacient -> {
                Object[] row = { pacient.getId(), pacient.getName(), pacient.getLastName(), pacient.getBirthDate() };
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
    
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10)); 
        JLabel idLabel = new JLabel("Enter Pacient ID:");
        JTextField idField = new JTextField(20);
        JLabel nameLabel = new JLabel("Enter Pacient name:");
        JTextField nameField = new JTextField(10);
        JLabel lastNameLabel = new JLabel("Enter Pacient lastName: ");
        JTextField lastNameField = new JTextField(10);
        JLabel birthDateLabel = new JLabel("Enter Pacient birthDate:");
        JTextField birthDateField = new JTextField(10);
        JLabel addressLabel = new JLabel("Enter Pacient address:");
        JTextField addressField = new JTextField(10);
        JLabel phoneLabel = new JLabel("Enter Pacient phone:");
        JTextField phoneField = new JTextField(10);
        JLabel emailLabel = new JLabel("Enter Pacient email:");
        JTextField emailField = new JTextField(10);
        JButton submitButton = createRoundedButton("Submit");
        JButton backButton = createRoundedButton("Back");
    
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        formPanel.add(birthDateLabel);
        formPanel.add(birthDateField);
        formPanel.add(addressLabel);
        formPanel.add(addressField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
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
                String lastName = lastNameField.getText().trim();
                String birthDate = birthDateField.getText().trim();
                String address = addressField.getText().trim();
                String phone = phoneField.getText().trim();
                String email = emailField.getText().trim();
    
                // Validar si los campos están vacíos
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Pacient name cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (lastName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Pacient lastName cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (birthDate.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Pacient birthDate cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (address.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Pacient address cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (phone.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Pacient phone cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Pacient email cannot be empty.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
    
                // Buscar pacient y actualizar si es encontrado
                findPacientUC.execute(id).ifPresentOrElse(
                        pacient -> {
                            pacient.setName(name);
                            pacient.setLastName(lastName);
                            pacient.setBirthDate(birthDate);
                            pacient.setAddress(address);
                            pacient.setPhone(phone);
                            pacient.setEmail(email);
    
                            updatePacientUC.execute(pacient);
                            JOptionPane.showMessageDialog(panel, "Pacient updated successfully.");
    
                            // Limpiar los campos
                            nameField.setText("");
                            lastNameField.setText("");
                            birthDateField.setText("");
                            addressField.setText("");
                            phoneField.setText("");
                            emailField.setText("");
                        },
                        () -> JOptionPane.showMessageDialog(panel, "Pacient not found.", "Error",
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

        JLabel idLabel = new JLabel("Enter Pacient ID:");
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
                int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this Pacient?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    deletePacientUC.execute(id);
                    JOptionPane.showMessageDialog(this, "Pacient deleted successfully.");
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

    private void showPacientDetails(Pacient Pacient) {
        String details = String.format("""
                Pacient found:
                ID: %d
                Name: %s
                LastName: %s
                BirthDate: %s
                Address: %s
                Phone: %s
                Email: %s
                """, Pacient.getId(), Pacient.getName(), Pacient.getLastName(),
                Pacient.getBirthDate(), Pacient.getAddress(), Pacient.getPhone(), Pacient.getEmail());
        JOptionPane.showMessageDialog(this, details, "Pacient Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new PacientAdapter();
    }

}

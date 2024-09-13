package com.mac.ui;

import javax.swing.*;

import com.mac.doctor.infrastructure.adapter.DoctorAdapter;
import com.mac.especialty.infrastructure.adapter.SpecialtyAdapter;
import com.mac.pacient.insfrastructure.adapter.PacientAdapter;
import com.mac.appointment.infrastructure.adapter.AppointmentAdapter;

import java.awt.*;

import javax.swing.JPanel;

public class OptionsController extends JFrame{

    private JPanel mainPanel;
    private CardLayout cardLayout;

    public OptionsController() {
        ImageIcon windowIcon = new ImageIcon("src/main/resources/img/Hospital.png");
        setIconImage(windowIcon.getImage());
        setTitle("Select User Options");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel addPanel = createOperationPanel("Select Crud", "Search", createAddPanel());

        mainPanel.add(addPanel, "Search");

        add(mainPanel);
        cardLayout.show(mainPanel, "Search");

        setVisible(true);
    }

    private JPanel createHeaderPanel(String title) {
        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon icon = new ImageIcon("src/main/resources/img/Admi.png");
        JLabel imageLabel = new JLabel(icon);

        headerPanel.setBorder(BorderFactory.createEmptyBorder(60, 60, 50, 60));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(imageLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createOperationPanel(String title, String cardName, JPanel operationPanel) {
        JPanel panel = new JPanel(new BorderLayout());

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
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton specialtyButton = createRoundedButton("Specialty CRUD");
        JButton doctorButton = createRoundedButton("Doctor CRUD");
        JButton patientButton = createRoundedButton("Patient CRUD");
        JButton appointmentButton = createRoundedButton("Appointment CRUD");
        JButton exitButton = createRoundedButton("Back");

        panel.add(specialtyButton);
        panel.add(doctorButton);
        panel.add(patientButton);
        panel.add(appointmentButton);
        panel.add(exitButton);

        specialtyButton.addActionListener(e -> new SpecialtyAdapter());
        doctorButton.addActionListener(e -> new DoctorAdapter());
        patientButton.addActionListener(e -> new PacientAdapter());
        appointmentButton.addActionListener(e -> new AppointmentAdapter());

        return panel;
    }

    public static void main(String[] args) {
        new OptionsController();
    }
}

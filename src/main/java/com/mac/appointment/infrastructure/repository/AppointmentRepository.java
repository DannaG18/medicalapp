package com.mac.appointment.infrastructure.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.mac.appointment.domain.entity.Appointment;
import com.mac.appointment.domain.service.AppointmentService;

public class AppointmentRepository implements AppointmentService{
    private Connection  connection;

    public AppointmentRepository() {
        try{
            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("configdb.properties"));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointment (doctor_id, pacientId, date_time, status) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, appointment.getDoctorId());
            ps.setInt(2, appointment.getPatientId());
            ps.setString(3, appointment.getDateTime());
            ps.setString(4, appointment.getStatus());
            ps.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAppointment(int id) {
        String sql = "DELETE FROM appointment WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public Optional<Appointment> findAppointment(int id) {
        String sql = "SELECT * FROM appointment WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    Appointment appointment = new Appointment(rs.getInt("id"), rs.getInt("doctor_id"), rs.getInt("pacient_id"), rs.getString("date_time"), rs.getString("status"));
                    return Optional.of(appointment);
                }    
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointment SET doctor_id = ?, pacientId = ?, date_time = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, appointment.getDoctorId());
            ps.setInt(2, appointment.getPatientId());
            ps.setString(3, appointment.getDateTime());
            ps.setString(4, appointment.getStatus());
            ps.setInt(5, appointment.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Appointment> findAllAppointment() {
        String sql = "SELECT * FROM appointment";
        List<Appointment> appointment = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()){
                    Appointment appointment1 = new Appointment(rs.getInt("id"), rs.getInt("doctor_id"), rs.getInt("pacient_id"), rs.getString("date_time"), rs.getString("status"));
                    appointment.add(appointment1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointment;
    }
}

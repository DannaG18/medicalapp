package com.mac.doctor.infrastructure.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.mac.doctor.domain.entity.Doctor;
import com.mac.doctor.domain.service.DoctorService;

public class DoctorRepository implements DoctorService{
    private Connection  connection;

    public DoctorRepository() {
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
    public void createDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctor (name, specialty_id, start_time, end_time) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, doctor.getName());
            ps.setInt(2, doctor.getSpecialtyId());
            ps.setString(3, doctor.getStartTime());
            ps.setString(4, doctor.getEndTime());
            System.out.println(doctor.getEndTime());
            ps.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDoctor(int id) {
        String sql = "DELETE FROM doctor WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public Optional<Doctor> findDoctor(int id) {
                String sql = "SELECT * FROM doctor WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    Doctor doctor = new Doctor(rs.getInt("id"), rs.getString("name"), rs.getInt("specialty_id"), rs.getString("start_time"), rs.getString("end_time"));
                    return Optional.of(doctor);
                }    
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        String sql = "UPDATE doctor SET name = ? specialty_id = ?, start_time = ?, end_time = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, doctor.getName());
            ps.setInt(2, doctor.getSpecialtyId());
            ps.setString(3, doctor.getStartTime());
            ps.setString(4, doctor.getEndTime());
            ps.setInt(5, doctor.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Doctor> findAllDoctor() {
                String sql = "SELECT * FROM doctor";
        List<Doctor> doctor = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()){
                    Doctor doctor1 = new Doctor(rs.getInt("id"), rs.getString("name"), rs.getInt("specialty_id"), rs.getString("start_time"), rs.getString("end_time"));
                    doctor.add(doctor1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctor;
    }
}
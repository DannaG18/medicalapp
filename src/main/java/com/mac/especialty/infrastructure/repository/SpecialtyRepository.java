package com.mac.especialty.infrastructure.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.mac.especialty.domain.entity.Specialty;
import com.mac.especialty.domain.service.SpecialtyService;

public class SpecialtyRepository implements SpecialtyService{
    private Connection connection;

    public SpecialtyRepository () {
        try {
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
    public void createSpecialty(Specialty specialty) {
        String sql = "INSERT INTO specialty (name) VALUES (?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, specialty.getName());
            ps.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteSpecialty(int id) {
        String sql = "DELETE FROM specialty WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public Optional<Specialty> findSpecialty(int id) {
        String sql = "SELECT * FROM specialty WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    Specialty specialty = new Specialty(rs.getInt("id"), rs.getString("name"));
                    return Optional.of(specialty);
                }    
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void updateSpecialty(Specialty specialty) {
        String sql = "UPDATE specialty SET name = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, specialty.getName());
            ps.setInt(2, specialty.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Specialty> findAllSpecialty() {
        String sql = "SELECT * FROM specialty";
        List<Specialty> specialty = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()){
                    Specialty specialty1 = new Specialty(rs.getInt("id"), rs.getString("name"));
                    specialty.add(specialty1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialty;
    }
    
}

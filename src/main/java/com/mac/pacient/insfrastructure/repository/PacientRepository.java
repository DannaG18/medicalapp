package com.mac.pacient.insfrastructure.repository;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import java.util.ArrayList;
import java.sql.Connection;

import com.mac.pacient.domain.entity.Pacient;
import com.mac.pacient.domain.service.PacientService;

public class PacientRepository implements PacientService{
    private Connection connection;

    public PacientRepository() {
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
    public void createPacient(Pacient pacient) {
        String sql = "INSERT INTO pacient (name, last_name, birth_date, adress, phone, email) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, pacient.getName());
            ps.setString(2, pacient.getLastName());
            ps.setString(3, pacient.getBirthDate());
            ps.setString(4, pacient.getAddress());
            ps.setString(5, pacient.getPhone());
            ps.setString(6, pacient.getEmail());
            ps.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePacient(int id) {
        String sql = "DELETE FROM pacient WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public Optional<Pacient> findPacient(int id) {
        String sql = "SELECT * FROM pacient WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    Pacient pacient = new Pacient(rs.getInt("id"), rs.getString("name"), rs.getString("last_name"), rs.getString("birth_date"), rs.getString("adress"), rs.getString("phone"), rs.getString("email"));
                    return Optional.of(pacient);
                }    
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void updatePacient(Pacient pacient) {
        String sql = "UPDATE pacient SET name = ? last_name = ?, birth_date = ?, adress = ?, phone = ?, email = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, pacient.getName());
            ps.setString(2, pacient.getLastName());
            ps.setString(3, pacient.getBirthDate());
            ps.setString(4, pacient.getAddress());
            ps.setString(5, pacient.getPhone());
            ps.setString(6, pacient.getEmail());
            ps.setInt(7, pacient.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pacient> findAllPacient() {
        String sql = "SELECT * FROM pacient";
        List<Pacient> pacient = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()){
                    Pacient pacient1 = new Pacient(rs.getInt("id"), rs.getString("name"), rs.getString("last_name"), rs.getString("birth_date"), rs.getString("adress"), rs.getString("phone"), rs.getString("email"));
                    pacient.add(pacient1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacient;
    }
    
}


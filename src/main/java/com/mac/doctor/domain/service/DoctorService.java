package com.mac.doctor.domain.service;

import java.util.List;
import java.util.Optional;

import com.mac.doctor.domain.entity.Doctor;

public interface DoctorService {
    void createDoctor (Doctor doctor);
    void deleteDoctor (int id);
    Optional <Doctor> findDoctor (int id);
    void updateDoctor (Doctor doctor);
    List <Doctor> findAllDoctor();
}

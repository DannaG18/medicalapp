package com.mac.doctor.application;

import com.mac.doctor.domain.entity.Doctor;
import com.mac.doctor.domain.service.DoctorService;
import java.util.Optional;

public class FindDoctorUC {
    private DoctorService doctorService;

    public FindDoctorUC(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public Optional <Doctor> execute (int id) {
        return doctorService.findDoctor(id);
    } 
}

package com.mac.doctor.application;

import com.mac.doctor.domain.entity.Doctor;
import com.mac.doctor.domain.service.DoctorService;

public class CreateDoctorUC {
    private DoctorService doctorService;

    public CreateDoctorUC(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public void execute(Doctor doctor) {
        doctorService.createDoctor(doctor);
    }
}

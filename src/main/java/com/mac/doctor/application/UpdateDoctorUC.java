package com.mac.doctor.application;

import com.mac.doctor.domain.entity.Doctor;
import com.mac.doctor.domain.service.DoctorService;

public class UpdateDoctorUC {
        private DoctorService doctorService;

    public UpdateDoctorUC(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public void execute(Doctor doctor) {
        doctorService.updateDoctor(doctor);
    }
}

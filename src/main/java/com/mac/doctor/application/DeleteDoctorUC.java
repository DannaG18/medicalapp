package com.mac.doctor.application;

import com.mac.doctor.domain.service.DoctorService;

public class DeleteDoctorUC {
    private DoctorService doctorService;

    public DeleteDoctorUC(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public void execute (int id) {
        doctorService.deleteDoctor(id);
    }
}

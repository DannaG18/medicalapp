package com.mac.doctor.application;

import com.mac.doctor.domain.entity.Doctor;
import com.mac.doctor.domain.service.DoctorService;
import java.util.List;

public class FindAllDoctorUC {
    private DoctorService doctorService;

    public FindAllDoctorUC(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public List<Doctor> execute () {
        return doctorService.findAllDoctor();
    }
}

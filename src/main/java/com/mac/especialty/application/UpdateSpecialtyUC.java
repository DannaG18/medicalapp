package com.mac.especialty.application;

import com.mac.especialty.domain.entity.Specialty;
import com.mac.especialty.domain.service.SpecialtyService;

public class UpdateSpecialtyUC {
    private SpecialtyService specialtyService;

    public UpdateSpecialtyUC(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    public void execute (Specialty specialty) {
        specialtyService.updateSpecialty(specialty);
    }
}

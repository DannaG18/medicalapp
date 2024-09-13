package com.mac.especialty.application;

import com.mac.especialty.domain.entity.Specialty;
import com.mac.especialty.domain.service.SpecialtyService;

public class CreateSpecialtyUC {
    private SpecialtyService specialtyService;

    public CreateSpecialtyUC(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    public void execute (Specialty specialty) {
        specialtyService.createSpecialty(specialty);
    }
}

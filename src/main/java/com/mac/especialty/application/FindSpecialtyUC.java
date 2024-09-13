package com.mac.especialty.application;

import com.mac.especialty.domain.entity.Specialty;
import com.mac.especialty.domain.service.SpecialtyService;
import java.util.Optional;

public class FindSpecialtyUC {
    private SpecialtyService specialtyService;

    public FindSpecialtyUC(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    public Optional <Specialty> execute (int id) {
        return specialtyService.findSpecialty(id);
    }
}

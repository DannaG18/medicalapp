package com.mac.especialty.application;

import com.mac.especialty.domain.entity.Specialty;
import com.mac.especialty.domain.service.SpecialtyService;
import java.util.List;

public class FindAllSpecialtyUC {
    private SpecialtyService specialtyService;

    public FindAllSpecialtyUC(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    public List <Specialty> execute() {
        return specialtyService.findAllSpecialty();
    }
}

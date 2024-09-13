package com.mac.especialty.application;

import com.mac.especialty.domain.service.SpecialtyService;

public class DeleteSpecialtyUC {
    private SpecialtyService specialtyService;

    public DeleteSpecialtyUC(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    public void execute (int id) {
        specialtyService.deleteSpecialty(id);
    }
}

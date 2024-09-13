package com.mac.pacient.application;

import com.mac.pacient.domain.entity.Pacient;
import com.mac.pacient.domain.service.PacientService;
import java.util.List;

public class FindAllPacientUC {
    private PacientService pacientService;

    public FindAllPacientUC(PacientService pacientService) {
        this.pacientService = pacientService;
    }

    public List <Pacient> execute() {
        return pacientService.findAllPacient();
    }
}

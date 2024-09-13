package com.mac.pacient.application;

import com.mac.pacient.domain.entity.Pacient;
import com.mac.pacient.domain.service.PacientService;
import java.util.Optional;

public class FindPacientUC {
    private PacientService pacientService;

    public FindPacientUC(PacientService pacientService) {
        this.pacientService = pacientService;
    }

    public Optional <Pacient> execute(int id) {
        return pacientService.findPacient(id);
    }
}

package com.mac.pacient.application;

import com.mac.pacient.domain.entity.Pacient;
import com.mac.pacient.domain.service.PacientService;

public class UpdatePacientUC {
    private PacientService pacientService;

    public UpdatePacientUC(PacientService pacientService) {
        this.pacientService = pacientService;
    }

    public void execute(Pacient pacient) {
        pacientService.updatePacient(pacient);
    }
}

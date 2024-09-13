package com.mac.pacient.application;

import com.mac.pacient.domain.entity.Pacient;
import com.mac.pacient.domain.service.PacientService;

public class CreatePacientUC {
    private PacientService pacientService;

    public CreatePacientUC(PacientService pacientService) {
        this.pacientService = pacientService;
    }

    public  void execute(Pacient pacient) {
        pacientService.createPacient(pacient);
    }

}

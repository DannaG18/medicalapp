package com.mac.pacient.application;

import com.mac.pacient.domain.service.PacientService;

public class DeletePacientUC {
    private PacientService pacientService;

    public DeletePacientUC(PacientService pacientService) {
        this.pacientService = pacientService;
    }

    public void execute(int id) {
        pacientService.deletePacient(id);
    }
}

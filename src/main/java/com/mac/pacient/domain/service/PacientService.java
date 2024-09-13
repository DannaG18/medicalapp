package com.mac.pacient.domain.service;

import java.util.List;
import java.util.Optional;

import com.mac.pacient.domain.entity.Pacient;

public interface PacientService {
    void createPacient (Pacient pacient);
    void deletePacient (int id);
    Optional <Pacient> findPacient (int id);
    void updatePacient (Pacient pacient);
    List <Pacient> findAllPacient();
}

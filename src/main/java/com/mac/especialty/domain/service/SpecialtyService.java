package com.mac.especialty.domain.service;

import java.util.List;
import java.util.Optional;

import com.mac.especialty.domain.entity.Specialty;

public interface SpecialtyService {
    void createSpecialty (Specialty specialty);
    void deleteSpecialty (int id);
    Optional <Specialty> findSpecialty (int id);
    void updateSpecialty (Specialty specialty);
    List <Specialty> findAllSpecialty();
}

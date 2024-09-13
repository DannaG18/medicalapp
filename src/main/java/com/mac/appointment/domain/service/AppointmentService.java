package com.mac.appointment.domain.service;

import java.util.List;
import java.util.Optional;

import com.mac.appointment.domain.entity.Appointment;

public interface AppointmentService {
    void createAppointment (Appointment appointment);
    void deleteAppointment (int id);
    Optional <Appointment> findAppointment (int id);
    void updateAppointment (Appointment appointment);
    List <Appointment> findAllAppointment();
}

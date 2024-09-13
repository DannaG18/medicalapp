package com.mac.appointment.application;

import com.mac.appointment.domain.entity.Appointment;
import com.mac.appointment.domain.service.AppointmentService;
import java.util.Optional;

public class FindAppointmentUC {
    private AppointmentService appointmentService;

    public FindAppointmentUC(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public Optional <Appointment> execute (int id) {
        return appointmentService.findAppointment(id);
    }
}

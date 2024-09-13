package com.mac.appointment.application;

import com.mac.appointment.domain.entity.Appointment;
import com.mac.appointment.domain.service.AppointmentService;
import java.util.List;

public class FindAllAppointmentUC {
    private AppointmentService appointmentService;

    public FindAllAppointmentUC(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public List<Appointment> execute() {
        return appointmentService.findAllAppointment();
    }
}

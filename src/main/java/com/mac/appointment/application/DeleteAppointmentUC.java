package com.mac.appointment.application;

import com.mac.appointment.domain.service.AppointmentService;

public class DeleteAppointmentUC {
    private AppointmentService appointmentService;

    public DeleteAppointmentUC(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public void execute(int id) {
        appointmentService.deleteAppointment(id);
    }
}

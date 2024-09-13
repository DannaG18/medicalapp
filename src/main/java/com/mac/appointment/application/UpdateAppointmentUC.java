package com.mac.appointment.application;

import com.mac.appointment.domain.entity.Appointment;
import com.mac.appointment.domain.service.AppointmentService;

public class UpdateAppointmentUC {
    private AppointmentService appointmentService;

    public UpdateAppointmentUC(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public void execute(Appointment appointment) {
        appointmentService.updateAppointment(appointment);
    }
}

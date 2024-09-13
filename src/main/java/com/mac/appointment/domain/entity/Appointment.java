package com.mac.appointment.domain.entity;

public class Appointment {
    private int id;
    private int doctorId;
    private int patientId;
    private String dateTime;
    private String status;

    
    public Appointment() {
    }


    public Appointment(int id, int doctorId, int patientId, String dateTime, String status) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.dateTime = dateTime;
        this.status = status;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getDoctorId() {
        return doctorId;
    }


    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }


    public int getPatientId() {
        return patientId;
    }


    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }


    public String getDateTime() {
        return dateTime;
    }


    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    
}

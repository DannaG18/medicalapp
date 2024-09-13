package com.mac.doctor.domain.entity;

public class Doctor {
    private int id;
    private String name;
    private int specialtyId;
    private String startTime;
    private String endTime;

    
    public Doctor() {
    }


    public Doctor(int id, String name, int specialtyId, String startTime, String endTime) {
        this.id = id;
        this.name = name;
        this.specialtyId = specialtyId;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getSpecialtyId() {
        return specialtyId;
    }


    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }


    public String getStartTime() {
        return startTime;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public String getEndTime() {
        return endTime;
    }


    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    
}

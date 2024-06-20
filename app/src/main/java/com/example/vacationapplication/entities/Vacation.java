package com.example.vacationapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacation")
public class Vacation {
    @PrimaryKey(autoGenerate = true)
    private int vacationId;
    private String vacationLocation;
    private String hotel;
    private String vacationStart;
    private String vacationEnd;

    public Vacation(int vacationId, String vacationLocation, String hotel, String vacationStart, String vacationEnd) {
        this.vacationId = vacationId;
        this.vacationLocation = vacationLocation;
        this.hotel = hotel;
        this.vacationStart = vacationStart;
        this.vacationEnd = vacationEnd;
    }

    public int getVacationId() {
        return vacationId;
    }

    public String getVacationLocation() {
        return vacationLocation;
    }

    public String getHotel() {
        return hotel;
    }

    public String getVacationStart() {
        return vacationStart;
    }

    public String getVacationEnd() {
        return vacationEnd;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public void setVacationLocation(String vacationLocation) {
        this.vacationLocation = vacationLocation;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public void setVacationStart(String vacationStart) {
        this.vacationStart = vacationStart;
    }

    public void setVacationEnd(String vacationEnd) {
        this.vacationEnd = vacationEnd;
    }
}

package com.example.vacationapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "excursion")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excursionId;
    private String excursionName;
    private String excursionDate;
    private int vacationId;

    public Excursion(int excursionId, String excursionName, String excursionDate, int vacationId) {
        this.excursionId = excursionId;
        this.excursionName = excursionName;
        this.excursionDate = excursionDate;
        this.vacationId = vacationId;
    }

    public int getExcursionId() {
        return excursionId;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setExcursionId(int excursionId) {
        this.excursionId = excursionId;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }
}

package com.example.myvacation.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Vacation")

public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationTitle;
    private String vacationHotelName;
    private String vacationStartDate;
    private String vacationEndDate;

    public Vacation(int vacationID, String vacationTitle, String vacationHotelName, String vacationStartDate, String vacationEndDate) {
        this.vacationID = vacationID;
        this.vacationTitle = vacationTitle;
        this.vacationHotelName = vacationHotelName;
        this.vacationStartDate = vacationStartDate;
        this.vacationEndDate = vacationEndDate;
    }

    public Vacation() {
    }

    public int getVacationID() {
        return vacationID;
    }

    public String toString(){
        return vacationTitle;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationTitle() {
        return vacationTitle;
    }

    public void setVacationTitle(String vacationTitle) {
        this.vacationTitle = vacationTitle;
    }

    public String getVacationHotelName() {
        return vacationHotelName;
    }

    public void setVacationHotelName(String vacationHotelName) {
        this.vacationHotelName = vacationHotelName;
    }

    public String getVacationStartDate() {
        return vacationStartDate;
    }

    public void setVacationStartDate(String vacationStartDate) {
        this.vacationStartDate = vacationStartDate;
    }

    public String getVacationEndDate() {
        return vacationEndDate;
    }

    public void setVacationEndDate(String vacationEndDate) {
        this.vacationEndDate = vacationEndDate;
    }



}

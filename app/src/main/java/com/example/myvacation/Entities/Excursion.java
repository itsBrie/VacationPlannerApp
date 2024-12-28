package com.example.myvacation.Entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Excursion")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private int vacationID;
    private String excursionName;
    private String excursionDate;


    public Excursion(String excursionName, String excursionDate, int vacationID, int excursionID) {
        this.excursionID = excursionID;
        this.excursionName = excursionName;
        this.excursionDate = excursionDate;
        this.vacationID = vacationID;
    }

    public Excursion() {
    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int id) {
        this.excursionID = id;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName=excursionName;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }
}
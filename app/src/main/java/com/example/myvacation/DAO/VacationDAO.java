package com.example.myvacation.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myvacation.Entities.Vacation;

import java.util.List;

@Dao
public interface VacationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addVacation(Vacation vacation); //ADD

    @Update
    void updateVacation(Vacation vacation); //UPDATE

    @Delete
    void deleteVacation(Vacation vacation); //DELETE

    @Query("SELECT * FROM Vacation")
    List<Vacation> getAllVacations();//READ

}

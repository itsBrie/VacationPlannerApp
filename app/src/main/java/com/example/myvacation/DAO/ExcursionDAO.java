package com.example.myvacation.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myvacation.Entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addExcursion(Excursion excursion); //ADD

    @Update
    void updateExcursion(Excursion excursion); //UPDATE

    @Delete
    void deleteExcursion(Excursion excursion); //DELETE

    @Query("SELECT * FROM Excursion WHERE vacationID = :vacationID")
    List<Excursion> getAssociatedExcursions(int vacationID); //READ

    @Query("SELECT * FROM Excursion")
    List<Excursion> getAllExcursions();
}
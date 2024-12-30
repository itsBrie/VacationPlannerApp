package com.example.myvacation.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myvacation.DAO.ExcursionDAO;
import com.example.myvacation.DAO.VacationDAO;
import com.example.myvacation.Entities.Excursion;
import com.example.myvacation.Entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 2, exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();

    public abstract ExcursionDAO excursionDAO();

    private static volatile VacationDatabaseBuilder INSTANCE;

    //Synchronize or Asynchronous

    static VacationDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) { //Is the database is null, then...
            synchronized (VacationDatabaseBuilder.class) {
                if (INSTANCE == null) { //Build Database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VacationDatabaseBuilder.class, "MyVacationDB")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
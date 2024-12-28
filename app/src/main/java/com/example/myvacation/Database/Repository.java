package com.example.myvacation.Database;

import android.app.Application;

import com.example.myvacation.DAO.ExcursionDAO;
import com.example.myvacation.DAO.VacationDAO;
import com.example.myvacation.Entities.Excursion;
import com.example.myvacation.Entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private ExcursionDAO mExcursionDAO;
    private VacationDAO mVacationDAO;

    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
        mExcursionDAO = db.excursionDAO();
        mVacationDAO = db.vacationDAO();
    }


//USING DAO for Vacation Implementation

    //Get All Vacations
    public List<Vacation> getAllVacations() {
        databaseExecutor.execute(() -> {
            mAllVacations = mVacationDAO.getAllVacations();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllVacations;
    }

    //ADD Vacation
    public void add(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.addVacation(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //UPDATE Vacation

    public void update(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.updateVacation(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //DELETE Vacation
    public void delete(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.deleteVacation(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//USING DAO for Excursion Implementation

    //GET ALL Excursions
    public List<Excursion> getAllExcursions() {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    //GET ASSOCIATED Excursions
    public List<Excursion> getAssociatedExcursions(int vacationID) {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAssociatedExcursions(vacationID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    //ADD Excursions
    public void add(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.addExcursion(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //UPDATE Excursions
    public void update(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.updateExcursion(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //DELETE Excursions
    public void delete(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.deleteExcursion(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
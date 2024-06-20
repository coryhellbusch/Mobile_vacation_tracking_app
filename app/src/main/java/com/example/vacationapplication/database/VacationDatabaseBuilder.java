package com.example.vacationapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.vacationapplication.dao.ExcursionDAO;
import com.example.vacationapplication.dao.VacationDAO;
import com.example.vacationapplication.entities.Excursion;
import com.example.vacationapplication.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version=3,exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {
    public abstract ExcursionDAO excursionDAO();
    public abstract VacationDAO vacationDAO();
    private static volatile VacationDatabaseBuilder INSTANCE;

    static VacationDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (VacationDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),VacationDatabaseBuilder.class, "MyVacationDatabase.db")
                            .fallbackToDestructiveMigration()
//                            .allowMainThreadQueries()  this would make it asynchronous
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

package com.example.vacationapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vacationapplication.entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM EXCURSION ORDER BY excursionId ASC")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM EXCURSION WHERE vacationId=:vacay ORDER BY excursionId ASC")
    List<Excursion> getAssociatedExcursions(int vacay);

}

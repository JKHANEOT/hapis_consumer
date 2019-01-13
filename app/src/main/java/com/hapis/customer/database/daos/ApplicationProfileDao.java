package com.hapis.customer.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hapis.customer.database.tables.ApplicationProfileTable;

import java.util.List;

@Dao
public interface ApplicationProfileDao {

    @Insert
    void insert(ApplicationProfileTable applicationProfileTable);

    @Update
    void update(ApplicationProfileTable applicationProfileTable);

    @Delete
    void delete(ApplicationProfileTable applicationProfileTable);

    @Query("DELETE FROM application_profile")
    void deleteAllApplicationProfile();

    @Query("SELECT * FROM application_profile")
    LiveData<List<ApplicationProfileTable>> getAllApplicationProfile();

    @Query("SELECT * FROM application_profile")
    List<ApplicationProfileTable> getAllApplicationProfileWithout();

    @Query("SELECT (appStatus) FROM application_profile")
    int getAppProfileStatus();

    @Query("UPDATE application_profile SET appStatus=:appStatus")
    void setAppProfileStatus(int appStatus);
}

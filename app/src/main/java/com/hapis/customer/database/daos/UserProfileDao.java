package com.hapis.customer.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hapis.customer.database.tables.UserProfileTable;

import java.util.List;

@Dao
public interface UserProfileDao {

    @Insert
    void insert(UserProfileTable userProfileTable);

    @Update
    void update(UserProfileTable userProfileTable);

    @Delete
    void delete(UserProfileTable userProfileTable);

    @Query("DELETE FROM user_profile")
    void deleteAllUserProfile();

    @Query("SELECT * FROM user_profile")
    LiveData<List<UserProfileTable>> getAllUserProfile();

    @Query("SELECT * FROM user_profile WHERE mobileNumber=:mobileNumber AND password=:password")
    UserProfileTable getUserProfileByMobileNumber(String mobileNumber, String password);

    @Query("SELECT * FROM user_profile WHERE email=:email AND password=:password")
    LiveData<UserProfileTable> getUserProfileByEmail(String email, String password);

    @Query("SELECT * FROM user_profile WHERE uniqueId=:uniqueId")
    UserProfileTable getUserProfileByUniqueId(String uniqueId);

    @Query("SELECT COUNT(uniqueId) FROM user_profile WHERE uniqueId=:uniqueId")
    int getNumberOfRows(String uniqueId);
}

package com.hapis.customer.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hapis.customer.database.tables.AddressTable;

import java.util.List;

@Dao
public interface AddressDao {

    @Insert
    void insert(AddressTable addressTable);

    @Update
    void update(AddressTable addressTable);

    @Delete
    void delete(AddressTable addressTable);

    @Query("DELETE FROM address")
    void deleteAllUserProfile();

    @Query("SELECT * FROM address")
    LiveData<List<AddressTable>> getAllAddress();

    @Query("SELECT * FROM address WHERE addressType=:addressType")
    LiveData<AddressTable> getAddressByType(int addressType);

    @Query("SELECT * FROM address WHERE fk_uniqueId=:fk_uniqueId AND addressType=:addressType")
    LiveData<AddressTable> getAddressByUniqueIdAndType(String fk_uniqueId, int addressType);

    @Query("SELECT * FROM address WHERE fk_uniqueId=:fk_uniqueId")
    LiveData<AddressTable> getAddressByUniqueId(String fk_uniqueId);

}

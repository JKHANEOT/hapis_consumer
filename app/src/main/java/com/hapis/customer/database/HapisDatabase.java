package com.hapis.customer.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.hapis.customer.database.daos.AddressDao;
import com.hapis.customer.database.daos.ApplicationProfileDao;
import com.hapis.customer.database.daos.UserProfileDao;
import com.hapis.customer.database.tables.AddressTable;
import com.hapis.customer.database.tables.ApplicationProfileTable;
import com.hapis.customer.database.tables.UserProfileTable;

@Database(entities = {ApplicationProfileTable.class, UserProfileTable.class, AddressTable.class}, version = 1, exportSchema = false)
public abstract class HapisDatabase extends RoomDatabase {

    private static HapisDatabase instance;

    public abstract ApplicationProfileDao getApplicationProfileDao();

    public abstract UserProfileDao getUserProfileDao();

    public abstract AddressDao getAddressDao();

    public static synchronized HapisDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    HapisDatabase.class, "HapisDatabase.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static Callback roomCallback = new Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}

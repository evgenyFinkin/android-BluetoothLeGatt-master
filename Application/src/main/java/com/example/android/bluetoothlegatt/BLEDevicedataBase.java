package com.example.android.bluetoothlegatt;

import android.arch.persistence.room.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {BLEDevice.class}, version = 1)
public abstract class BLEDevicedataBase extends RoomDatabase {
    public static BLEDevicedataBase INSTANCE;
    public abstract BLEDeviceDeo bleDeviceDeo();

    public static BLEDevicedataBase getBLEDevicedataBase(Context context)   {
        if(INSTANCE == null)    {
            INSTANCE = Room
                    .databaseBuilder(
                            context.getApplicationContext()
                            ,BLEDevicedataBase.class
                            , "BLEList"
                    )
                    .build();
        }
        return INSTANCE;
    }
}

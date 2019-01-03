package com.example.android.bluetoothlegatt;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface BLEDeviceDeo {
    @Query("SELECT * FROM BLEDevice") List<BLEDevice> getall();

    @Query("SELECT alias FROM BLEDevice where device_name = :deviceName")
    String showAlias(String deviceName);

    @Insert(onConflict = REPLACE) void saveAlias(BLEDevice bleDevice);

    @Delete void delete(BLEDevice bleDevice);

}

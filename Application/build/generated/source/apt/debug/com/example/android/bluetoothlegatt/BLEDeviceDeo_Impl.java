package com.example.android.bluetoothlegatt;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class BLEDeviceDeo_Impl implements BLEDeviceDeo {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfBLEDevice;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfBLEDevice;

  public BLEDeviceDeo_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBLEDevice = new EntityInsertionAdapter<BLEDevice>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `BLEDevice`(`id`,`device_name`,`alias`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BLEDevice value) {
        stmt.bindLong(1, value.getId());
        if (value.getDeviceName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDeviceName());
        }
        if (value.getAlias() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAlias());
        }
      }
    };
    this.__deletionAdapterOfBLEDevice = new EntityDeletionOrUpdateAdapter<BLEDevice>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `BLEDevice` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BLEDevice value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public void saveAlias(BLEDevice bleDevice) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfBLEDevice.insert(bleDevice);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(BLEDevice bleDevice) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfBLEDevice.handle(bleDevice);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<BLEDevice> getall() {
    final String _sql = "SELECT * FROM BLEDevice";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfDeviceName = _cursor.getColumnIndexOrThrow("device_name");
      final int _cursorIndexOfAlias = _cursor.getColumnIndexOrThrow("alias");
      final List<BLEDevice> _result = new ArrayList<BLEDevice>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final BLEDevice _item;
        _item = new BLEDevice();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpDeviceName;
        _tmpDeviceName = _cursor.getString(_cursorIndexOfDeviceName);
        _item.setDeviceName(_tmpDeviceName);
        final String _tmpAlias;
        _tmpAlias = _cursor.getString(_cursorIndexOfAlias);
        _item.setAlias(_tmpAlias);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public String showAlias(String deviceName) {
    final String _sql = "SELECT alias FROM BLEDevice where device_name = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (deviceName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, deviceName);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final String _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getString(0);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}

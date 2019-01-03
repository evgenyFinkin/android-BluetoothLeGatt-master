package com.example.android.bluetoothlegatt;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class BLEDevicedataBase_Impl extends BLEDevicedataBase {
  private volatile BLEDeviceDeo _bLEDeviceDeo;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `BLEDevice` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `device_name` TEXT, `alias` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"cef6cb1fae57f53d1569e1cb6c0462c5\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `BLEDevice`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsBLEDevice = new HashMap<String, TableInfo.Column>(3);
        _columnsBLEDevice.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsBLEDevice.put("device_name", new TableInfo.Column("device_name", "TEXT", false, 0));
        _columnsBLEDevice.put("alias", new TableInfo.Column("alias", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBLEDevice = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBLEDevice = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBLEDevice = new TableInfo("BLEDevice", _columnsBLEDevice, _foreignKeysBLEDevice, _indicesBLEDevice);
        final TableInfo _existingBLEDevice = TableInfo.read(_db, "BLEDevice");
        if (! _infoBLEDevice.equals(_existingBLEDevice)) {
          throw new IllegalStateException("Migration didn't properly handle BLEDevice(com.example.android.bluetoothlegatt.BLEDevice).\n"
                  + " Expected:\n" + _infoBLEDevice + "\n"
                  + " Found:\n" + _existingBLEDevice);
        }
      }
    }, "cef6cb1fae57f53d1569e1cb6c0462c5", "a762eb5f2a8bf0b5946fc52b7241bfe1");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "BLEDevice");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `BLEDevice`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public BLEDeviceDeo bleDeviceDeo() {
    if (_bLEDeviceDeo != null) {
      return _bLEDeviceDeo;
    } else {
      synchronized(this) {
        if(_bLEDeviceDeo == null) {
          _bLEDeviceDeo = new BLEDeviceDeo_Impl(this);
        }
        return _bLEDeviceDeo;
      }
    }
  }
}

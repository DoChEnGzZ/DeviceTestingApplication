package com.example.devicetestingapplication;

import androidx.room.Database;
import androidx.room.Room;

public class myApplication extends android.app.Application {
    private myDataBase mydatabase;
    private TalnetUtils talnetUtils;
    private TFTPUtils tftpUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        mydatabase= Room.databaseBuilder(getApplicationContext(),
                myDataBase.class,
                "DeviceTest.db")
                .allowMainThreadQueries()
                .build();
    }

    public com.example.devicetestingapplication.myDataBase getMyDataBase() {
        return mydatabase;
    }

    public TalnetUtils getTalnetUtils() {
        return talnetUtils;
    }

    public TFTPUtils getTftpUtils() {
        return tftpUtils;
    }
}

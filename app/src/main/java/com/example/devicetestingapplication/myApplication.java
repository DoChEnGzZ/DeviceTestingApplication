package com.example.devicetestingapplication;

import androidx.room.Room;

public class myApplication extends android.app.Application {
    private myDataBase mydatabase;
    private TelnetUtils telnetUtils;
    private TFTPUtils tftpUtils;
    private boolean isConnected=false;
    private String pardus;

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

    public TelnetUtils getTelnetUtils() {
        return telnetUtils;
    }

    public TFTPUtils getTftpUtils() {
        return tftpUtils;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public String getPardus() {
        return pardus;
    }

    public void setPardus(String pardus) {
        this.pardus = pardus;
    }
}

package com.example.devicetestingapplication;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


public class myApplication extends android.app.Application {
    private myDataBase mydatabase;
    private TelnetUtils telnetUtils;
    private TFTPUtils tftpUtils;
    private boolean isConnected=false;
    private String pardus;
    private String ipaddress;

    @Override
    public void onCreate() {
        super.onCreate();
        mydatabase= Room.databaseBuilder(getApplicationContext(),
                myDataBase.class,
                "DeviceTest.db")
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1TO2)
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

    static final Migration MIGRATION_1TO2=new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("create table IF NOT EXISTS `FileDir` (`Pardusdir` TEXT NOT NULL PRIMARY KEY)");
        }
    };

    public String getPardus() {
        return pardus;
    }

    public void setPardus(String pardus) {
        this.pardus = pardus;
    }

    public String getIp(){
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ipaddress = intToIp(ipAddress);
        return ipaddress;
    }
    private String intToIp(int i) {

        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    public void setTelnetUtils(TelnetUtils telnetUtils) {
        this.telnetUtils = telnetUtils;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
}

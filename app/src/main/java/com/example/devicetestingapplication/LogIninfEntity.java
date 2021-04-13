package com.example.devicetestingapplication;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LogIninf")
public class LogIninfEntity {
    @PrimaryKey
    @NonNull
    private String ipaddr;
    private String username;
    private String passwd;


    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "LogIninfEntity{" +
                "ipaddr='" + ipaddr + '\'' +
                ", username='" + username + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}

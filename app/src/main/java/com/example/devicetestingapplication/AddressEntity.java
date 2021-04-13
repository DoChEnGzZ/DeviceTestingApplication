package com.example.devicetestingapplication;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Address")
public class AddressEntity {
    @PrimaryKey
    @NonNull
    private String ipaddr;
    private String macaddr;

    public AddressEntity() {
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getMacaddr() {
        return macaddr;
    }

    public void setMacaddr(String macaddr) {
        this.macaddr = macaddr;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "ipaddr='" + ipaddr + '\'' +
                ", macaddr='" + macaddr + '\'' +
                '}';
    }
}

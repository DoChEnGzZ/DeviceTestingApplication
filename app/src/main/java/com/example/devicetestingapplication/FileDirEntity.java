package com.example.devicetestingapplication;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FileDir")
public class FileDirEntity {
    @NonNull
    @PrimaryKey
    private String Pardusdir;

    @NonNull
    public String getPardusdir() {
        return Pardusdir;
    }

    public void setPardusdir(@NonNull String pardusdir) {
        Pardusdir = pardusdir;
    }
}

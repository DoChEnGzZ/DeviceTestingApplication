package com.example.devicetestingapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.devicetestingapplication.UserDao;

@Database(entities = {AddressEntity.class,LogIninfEntity.class},version = 1)
public abstract class myDataBase extends RoomDatabase {

    public abstract UserDao userDao();
}

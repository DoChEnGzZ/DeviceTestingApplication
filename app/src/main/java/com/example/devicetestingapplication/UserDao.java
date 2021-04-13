package com.example.devicetestingapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface UserDao {
    /*
    todo: 完成数据库的接口
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLogIninf(LogIninfEntity logIninfEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insetAddress(AddressEntity addressEntity);

    @Query("delete from LogIninf")
    void deleteLogIninf();

    @Query("select * from LogIninf")
    LogIninfEntity queryLogIninf();

    @Query("select * from Address")
    List<AddressEntity> queryAddress();

}

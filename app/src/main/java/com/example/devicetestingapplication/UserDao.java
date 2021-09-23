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

    @Query("delete from LogIninf")
    void deleteLogIninf();


    @Query("select * from LogIninf")
    LogIninfEntity queryLogIninf();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAddress(AddressEntity addressEntity);

    @Query("delete from Address")
    void deleteAddress();


    @Query("select * from Address")
    List<AddressEntity> queryAddress();

    @Query("select * from FileDir")
    List<FileDirEntity> queryPardusDir();


}

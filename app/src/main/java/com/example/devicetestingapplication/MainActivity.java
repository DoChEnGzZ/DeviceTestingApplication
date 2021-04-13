package com.example.devicetestingapplication;

import androidx.appcompat.app.AppCompatActivity;
import org.apache.commons.net.tftp.TFTPClient;
import org.apache.commons.net.telnet.TelnetClient;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        *todo1 : 读取数据库中的预存储信息
        * 1. 上一次修改的ip地址、mac地址
        * 2. 上一次读取的本地文件目录地址
        * */
        Intent intent=new Intent(this,ConnectActivity.class);
        startActivity(intent);
    }
}

package com.example.devicetestingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.devicetestingapplication.ftpserver.FTPservice;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private myApplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent=new Intent(this, MenuActivity.class);
        initviews();
        application=(myApplication)getApplication();
        Log.d("ipaddr test: ",application.getIp());
        application.setIpaddress(application.getIp());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            startForegroundService(new Intent(this,FTPservice.class));
        }
        else {
            startService(new Intent(this,FTPservice.class));
        }
//        final Thread thread=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                SystemClock.sleep(3000);
//            }
//        });
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        startActivity(intent);
    }

    private <T extends View> T f(int ViewID){
        return (T)findViewById(ViewID);
    }

    private void initviews(){
    }
}

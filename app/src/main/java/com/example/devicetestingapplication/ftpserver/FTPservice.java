package com.example.devicetestingapplication.ftpserver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import androidx.annotation.Nullable;

import com.example.devicetestingapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FTPservice extends Service {

    private FtpServer ftpServer;
    private static final String username="root";
    private static final String password="123456";
    private static final String tag="ftpserver test";
    private static final String CHANNEL_ID_STRING="1";
    private static final String CHANEL_NAME = "channel_name_1";
    private String boot;
    private int port=2021;

    @Override
    public void onCreate() {
        super.onCreate();
       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        NotificationChannel channel = null;
        channel = new NotificationChannel(CHANNEL_ID_STRING,CHANEL_NAME,NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_STRING).build();
        startForeground(1, notification);
       }
        boot= Environment.getExternalStorageDirectory().getAbsolutePath()+"/ftpboot";
       File file=new File(boot);
       if(!file.exists())
       {
           Log.d(tag,"ftp boot dont existed");
           if(!file.mkdirs()){
               Log.d(tag,"ftp boot has been existed");
           }
       }
       else
           Log.d(tag,"ftp boot has been existed");
        try {
            initserver();
            if(!ftpServer.isStopped())
            {
                Log.d(tag,"ftp server has been started");
            }
        } catch (FtpException e) {
            Log.d(tag,e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopftp();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initserver() throws FtpException{
        stopftp();
        startftp();
    }

    private void startftp() throws FtpException{
        FtpServerFactory serverFactory=new FtpServerFactory();
        BaseUser baseUser=new BaseUser();
        baseUser.setName(username);
        baseUser.setPassword(password);
        baseUser.setHomeDirectory(boot);
        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new WritePermission());
        baseUser.setAuthorities(authorities);
        serverFactory.getUserManager().save(baseUser);
        ListenerFactory factory = new ListenerFactory();
        factory.setPort(port);
        serverFactory.addListener("default", factory.createListener());
        ftpServer = serverFactory.createServer();
        ftpServer.start();
        Log.d(tag,"file dir is"+boot);
    }

    private void stopftp(){
        if(ftpServer!=null&&!ftpServer.isStopped()){
            ftpServer.stop();
            ftpServer=null;
        }
    }
}

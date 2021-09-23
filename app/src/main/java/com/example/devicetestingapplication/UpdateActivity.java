package com.example.devicetestingapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import me.rosuh.filepicker.config.FilePickerManager;

public class UpdateActivity extends AppCompatActivity {
    private static final String tag="FILE SELECTER test";
    private myApplication application;
    private String ftpboot;
    private Toast toast;
    private TelnetUtils telnetUtils;
    private Intent intentupdate2menu;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        intentupdate2menu=new Intent(this,MenuActivity.class);
        ftpboot= Environment.getExternalStorageDirectory().getAbsolutePath()+"/ftpboot";
        application=(myApplication) getApplication();
        if(!application.isConnected())
        {
            toast.makeText(UpdateActivity.this,"请先建立连接",Toast.LENGTH_LONG).show();
            startActivity(intentupdate2menu);
        }
        else{
        FilePickerManager.INSTANCE.from(this).maxSelectable(1).
                setCustomRootPath("/storage/emulated/0").
                setTheme(R.style.FilePickerThemeReply).
                forResult(FilePickerManager.REQUEST_CODE);}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 按了返回键
        if (resultCode == Activity.RESULT_CANCELED) {
            startActivity(intentupdate2menu);
        }
        // 接收返回的文件
        if (requestCode == FilePickerManager.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                application.setPardus(FilePickerManager.INSTANCE.obtainData().toString());
                application.setPardus(application.getPardus().substring(1,application.getPardus().length()-1));
                Log.d(tag,"文件名"+application.getPardus());
                filename=application.getPardus().substring(application.getPardus().lastIndexOf("/")+1);
                Log.d(tag,"文件名"+filename);
                String pardusinboot=ftpboot+"/"+filename;
                Log.d(tag, "文件路径: " + pardusinboot);
                telnetUtils=application.getTelnetUtils();
                if (application.getPardus().length()!=0) {
                    copy(application.getPardus(),pardusinboot,1024);
                    startActivity(intentupdate2menu);
                    telnetUtils=application.getTelnetUtils();
                    Thread update_thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            telnetUtils.sendCommand("cd u");
                            //todo: 完成升级功能
                            telnetUtils.sendCommand("rm filename");
                    telnetUtils.sendCommand("ftpget -u root -p 123456 -P 2021 "+application.getIp()+filename+" "+filename);
                        }
                    });
                    update_thread.start();
                    try {
                        update_thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "没有选择任何东西~", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "没有选择任何东西~", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void copy(String source, String dest, int bufferSize) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(new File(source));
            out = new FileOutputStream(new File(dest));

            byte[] buffer = new byte[bufferSize];
            int len;

            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            Log.w(tag + ":copy", "error occur while copy", e);
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
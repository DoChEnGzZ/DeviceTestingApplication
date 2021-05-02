package com.example.devicetestingapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import me.rosuh.filepicker.config.FilePickerManager;

public class UpdateActivity extends AppCompatActivity {
    private static final String tag="FILE SELECTER TEST";
    private myApplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        application=(myApplication) getApplication();
        FilePickerManager.INSTANCE.from(this).maxSelectable(1).
                setCustomRootPath("/storage/emulated/0").
                setTheme(R.style.FilePickerThemeReply).
                forResult(FilePickerManager.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intentupdate2menu=new Intent(this,MenuActivity.class);
        // 按了返回键
        if (resultCode == Activity.RESULT_CANCELED) {
            startActivity(intentupdate2menu);
        }
        // 接收返回的文件
        if (requestCode == FilePickerManager.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                application.setPardus(FilePickerManager.INSTANCE.obtainData().toString());
                if (application.getPardus().length()!=0) {
                        Log.d(tag, "文件路径: " + application.getPardus());
                        startActivity(intentupdate2menu);
                        //todo: 完成升级功能
                } else {
                    Toast.makeText(this, "没有选择任何东西~", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "没有选择任何东西~", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
package com.example.devicetestingapplication;

import androidx.appcompat.app.AppCompatActivity;
import org.apache.commons.net.tftp.TFTPClient;
import org.apache.commons.net.telnet.TelnetClient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_2connect;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        *todo1 : 读取数据库中的预存储信息
        * 1. 上一次修改的ip地址、mac地址
        * 2. 上一次读取的本地文件目录地址
        * */
        intent=new Intent(this,ConnectActivity.class);
        initviews();
        btn_2connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }

    private <T extends View> T f(int ViewID){
        return (T)findViewById(ViewID);
    }

    private void initviews(){
        this.btn_2connect=f(R.id.button_2connect);
    }
}

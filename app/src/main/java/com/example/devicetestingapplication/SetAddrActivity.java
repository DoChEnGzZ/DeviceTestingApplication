package com.example.devicetestingapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import me.rosuh.filepicker.config.FilePickerManager;

public class SetAddrActivity extends AppCompatActivity {

    private EditText editText_eth0_ipaddr;
    private EditText editText_eth1_ipaddr;
    private EditText editText_eth0_mac;
    private EditText editText_eth1_mac;
    private String ftpboot;
    private CheckBox checkBox;
    private Button button_yes;
    private Button button_no;
    private myApplication myapplication=null;
    private myDataBase myDataBase;
    private Toast toast;
    private List<AddressEntity> addrs;
    private TelnetUtils telnetUtils;
    private static final String tag="setaddt test";
    private String startback="" +
            "cat /u/version | while read var \n" +
            "do\n" +
            "var=`expr $var + 1`\n" +
            "echo $var>/u/version\n" +
            "done\n" +
            "\n" +
            "sleep 2\n" +
            "insmod /u/pardus.ko\n" +
            "/u/pardus\n";

    private Intent intentaddr2Menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setaddr);
        myapplication=(myApplication)getApplication();
        myDataBase=myapplication.getMyDataBase();
        telnetUtils=myapplication.getTelnetUtils();
        intentaddr2Menu=new Intent(this,MenuActivity.class);
        ftpboot= Environment.getExternalStorageDirectory().getAbsolutePath()+"/ftpboot";
        initViews();
        setOnclicklistener();

    }



    private <T extends View> T f(int ViewID)
    {
        return (T)findViewById(ViewID);
    }

    private void initViews()
    {
        editText_eth0_ipaddr=f(R.id.editText_eth0_ipaddr);
        editText_eth0_mac=f(R.id.editText_eth0_mac);
        editText_eth1_ipaddr=f(R.id.editText_eth1_ipaddr);
        editText_eth1_mac=f(R.id.editText_eth1_mac);
        checkBox=f(R.id.checkBox_remaddr);
        button_no=f(R.id.button_cancel_2);
        button_yes=f(R.id.button_yes_2);
        addrs=myDataBase.userDao().queryAddress();
        if(addrs.get(0).getIpaddr().length()!=0
                &&addrs.get(1).getIpaddr().length()!=0){
            editText_eth0_ipaddr.setText(addrs.get(0).getIpaddr());
            editText_eth0_mac.setText(addrs.get(0).getMacaddr());
            editText_eth1_ipaddr.setText(addrs.get(1).getIpaddr());
            editText_eth1_mac.setText(addrs.get(1).getMacaddr());
        }
    }

    private void setOnclicklistener()
    {

        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentaddr2Menu);
            }
        });

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(TextUtils.isEmpty(editText_eth1_ipaddr.getText())||
//                        TextUtils.isEmpty(editText_eth1_ipaddr.getText())||
                if(!myapplication.isConnected())
                {
                    toast.makeText(SetAddrActivity.this,"请先建立连接",Toast.LENGTH_LONG).show();
                    startActivity(intentaddr2Menu);
                }
                else{
                    telnetUtils=myapplication.getTelnetUtils();
                if(
                        TextUtils.isEmpty(editText_eth0_ipaddr.getText())||
                        TextUtils.isEmpty(editText_eth0_mac.getText())
                ){
                    toast.makeText(SetAddrActivity.this,"输入为空",Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    if(checkBox.isChecked())
                    {
                        AddressEntity eth0=new AddressEntity();
                        AddressEntity eth1=new AddressEntity();
                        myDataBase.userDao().deleteAddress();
                        eth0.setIpaddr(editText_eth0_ipaddr.getText().toString());
                        eth0.setMacaddr(editText_eth0_mac.getText().toString());
                        eth1.setIpaddr(editText_eth1_ipaddr.getText().toString());
                        eth1.setMacaddr(editText_eth1_mac.getText().toString());
                        myDataBase.userDao().insertAddress(eth0);
                        myDataBase.userDao().insertAddress(eth1);
                    }
                    String start=ftpboot+"/start.sh";
                    File oldstart=new File(start);
                    String start_bak=ftpboot+"/startbak.sh";
                    File startbak=new File(start_bak);
                    if(oldstart.renameTo(startbak)){
                        Log.d(tag,"建立备份文件成功");
                    }
                    if(!oldstart.exists())
                    {
                        try {
                            oldstart.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        FileOutputStream writer=new FileOutputStream(oldstart);
                        if(!TextUtils.isEmpty(editText_eth1_ipaddr.getText())||
                                !TextUtils.isEmpty(editText_eth1_ipaddr.getText()))
                        {
                            String wr1= "ifconfig eth1 down\n" +
                                            "ifconfig eth1 hw ether "+editText_eth1_mac.getText()+"\n"+
                                            "ifconfig eth1 up\n" +
                                            "ifconfig eth1 "+editText_eth1_ipaddr.getText();
                            writer.write(wr1.trim().getBytes());
                            writer.flush();
                            writer.write("\n".getBytes());
                            writer.flush();
                        }
                        if(!TextUtils.isEmpty(editText_eth0_ipaddr.getText())||
                                !TextUtils.isEmpty(editText_eth0_mac.getText()))
                        {
                            String wr0="ifconfig eth0 "+editText_eth0_ipaddr.getText()+"\n" +
                                    "ifconfig eth0 down\n"+
                                    "ifconfig eth0 hw ether "+editText_eth0_mac.getText()+"\n"+
                                    "ifconfig eth0 up";
                            writer.write(wr0.trim().getBytes());
                            writer.flush();
                            writer.write("\n".getBytes());
                            writer.flush();
                        }
                        writer.write(startback.trim().getBytes());
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        Log.d(tag,e.getMessage());
                    }
                    //todo:完成替换设备中的start.sh
                    Thread setaddr_thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                        telnetUtils.sendCommand("cd u");
                        telnetUtils.sendCommand("rm start.sh");
                        telnetUtils.sendCommand("ftpget -u root -p 123456 -P 2021 "+myapplication.getIp()+"start.sh start.sh");
                        }
                    });
                    setaddr_thread.start();
                    try {
                        setaddr_thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    toast.makeText(SetAddrActivity.this,"修改完成，重启设备后生效",Toast.LENGTH_LONG).show();
                    startbak.delete();
                    startActivity(intentaddr2Menu);
                }
            }}
        });
    }
}


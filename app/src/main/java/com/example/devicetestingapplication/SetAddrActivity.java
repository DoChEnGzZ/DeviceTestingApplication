package com.example.devicetestingapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.rosuh.filepicker.config.FilePickerManager;

public class SetAddrActivity extends AppCompatActivity {

    private EditText editText_eth0_ipaddr;
    private EditText editText_eth1_ipaddr;
    private EditText editText_eth0_mac;
    private EditText editText_eth1_mac;
    private CheckBox checkBox;
    private Button button_yes;
    private Button button_no;
    private myApplication myapplication=null;
    private myDataBase myDataBase;
    private Toast toast;
    private List<AddressEntity> addrs;

    private Intent intentaddr2Menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setaddr);
        myapplication=(myApplication)getApplication();
        myDataBase=myapplication.getMyDataBase();
        intentaddr2Menu=new Intent(this,MenuActivity.class);
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
                if(TextUtils.isEmpty(editText_eth1_ipaddr.getText())||
                        TextUtils.isEmpty(editText_eth1_ipaddr.getText())||
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
                    //todo: 完成输入地址后的相关修改工作
                }
            }
        });
    }
}

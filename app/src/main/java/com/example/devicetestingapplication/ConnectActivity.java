package com.example.devicetestingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ConnectActivity extends AppCompatActivity {

    private EditText editText_ipaddr;
    private EditText editText_username;
    private EditText editText_passwd;
    private Button button_yes;
    private Button button_cancel;
    private CheckBox remCon;
    private Toast toast;
    private TFTPUtils tftpUtils;
    private TelnetUtils telnetUtils;
    private myApplication myapplication=null;
    private myDataBase myDataBase;
    private Intent intentConnect2Menu;

    public LogIninfEntity logIninfEntity =new LogIninfEntity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        initViews();
        intentConnect2Menu=new Intent(this,MenuActivity.class);
        myapplication=(myApplication)getApplication();
        myDataBase=myapplication.getMyDataBase();
        tftpUtils=myapplication.getTftpUtils();
        telnetUtils =myapplication.getTelnetUtils();
        logIninfEntity=myDataBase.userDao().queryLogIninf();
//        Log.d("log:sql test",logIninfEntity.toString());
        if(logIninfEntity.getIpaddr().length()!=0)
        {
            editText_username.setText(logIninfEntity.getUsername());
            editText_passwd.setText(logIninfEntity.getPasswd());
            editText_ipaddr.setText(logIninfEntity.getIpaddr());
        }
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText_ipaddr.getText())||
                TextUtils.isEmpty(editText_username.getText())||
                TextUtils.isEmpty(editText_passwd.getText())){
                    toast.makeText(ConnectActivity.this,"输入为空",Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    logIninfEntity.setIpaddr(editText_ipaddr.getText().toString());
//                    Log.d("登陆信息测试",editText_ipaddr.getText().toString());
                    logIninfEntity.setPasswd(editText_passwd.getText().toString());
                    logIninfEntity.setUsername(editText_username.getText().toString());
                    tftpUtils=new TFTPUtils(ConnectActivity.this, logIninfEntity);
                    telnetUtils =new TelnetUtils(ConnectActivity.this, logIninfEntity);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            tftpUtils.open();
                            telnetUtils.connect();
                            myapplication.setConnected(tftpUtils.isConnected()&& telnetUtils.isConnected());
//                            Log.d("connect test","thread running normally");
                        }
                    }).start();
                    if(myapplication.isConnected())
                    {
                        /**
                         * 连接成功，将tftp和talnet通过intent传递给下一个activity
                         */
                        toast.makeText(ConnectActivity.this,"登陆成功",Toast.LENGTH_SHORT).
                                show();
                        if(remCon.isChecked())
                        {
                            myDataBase.userDao().deleteLogIninf();
                            myDataBase.userDao().insertLogIninf(logIninfEntity);
                            Log.d("sql test",myDataBase.userDao().queryLogIninf().toString());
                        }
                        /**
                         * todo: 测试是使用application传递连接类还是用bundle
                         */
//                         Bundle bundle=new Bundle();
//                         bundle.putSerializable("tftp",tftpUtils);
//                         bundle.putSerializable("talnet",telnetUtils);
//                         intent2Menu.putExtras(bundle);
//                        telnetUtils.sendCommand("cd /u");
//                        Log.d("talnet test", telnetUtils.sendCommand("ls -l"));
//                         startActivity(intent2Menu);
                    }
                    else{
                        toast.makeText(ConnectActivity.this,"连接失败",Toast.LENGTH_SHORT)
                                .show();
                        editText_ipaddr.setText("");
                        editText_passwd.setText("");
                        editText_username.setText("");
                    }
                }
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentConnect2Menu);
            }
        });

    }


    /**
     * 和控件id捆绑的快捷方法
     * @param ViewID viewid
     * @param <T> view
     * @return view
     */
    private <T extends View> T f(int ViewID){
        return (T)findViewById(ViewID);
    }

    private void initViews(){
        this.editText_ipaddr=f(R.id.editText_ipaddr);
        this.editText_passwd=f(R.id.editText_passwd);
        this.editText_username=f(R.id.editText_username);
        this.button_yes=f(R.id.button_yes);
        this.button_cancel=f(R.id.button_cancel);
        this.remCon=f(R.id.checkBox_remCon);
    }
}

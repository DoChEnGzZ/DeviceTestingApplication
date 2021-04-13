package com.example.devicetestingapplication;

import android.content.Context;
import android.util.Log;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;

public class TalnetUtils implements Serializable {

    private  TelnetClient client;
    private LogIninfEntity logIninfEntity;
    private  Context context;
    private static final String prompt="#";
    private static final int PORT=23;
    private InputStream inputStream;
    private PrintStream printStream;


    /**
     * 构造函数
     * @param context 上下文环境
     * @param logIninfEntity 登陆信息
     */
    public TalnetUtils(Context context, LogIninfEntity logIninfEntity) {
        this.context=context;
        this.logIninfEntity = logIninfEntity;
        client=new TelnetClient();
    }

    /**
     * 连接服务器
     * @return 连接成功或失败
     */
    public boolean connect(){
        try {
            client.connect(logIninfEntity.getIpaddr(),PORT);
            inputStream=client.getInputStream();
            printStream=new PrintStream(client.getOutputStream());
            try {
                write(logIninfEntity.getUsername());
                write(logIninfEntity.getPasswd());
            } catch (Exception e) {
                Log.d("LOGIN error",e.getMessage());
                return false;
            }
            if(isConnected())
                return true;
            else
                return false;
        } catch (IOException e) {
            Log.d("IO error",e.getMessage());
            return false;
        }
    }


    /**
     * 读取命令的执行结果
     * @param info 命令的执行到这个字符时结束
     * @return 返回执行结果
     */
    public String read(String info) {
        try {
            char lastChar = info.charAt(info.length() - 1);
            StringBuffer stringBuffer = new StringBuffer();
            char ch = (char) inputStream.read();
            while (true) {
                stringBuffer.append(ch);
                if (ch == lastChar) {
                    if (stringBuffer.toString().endsWith(info)) {
                        return stringBuffer.toString();
                    }
                }
                ch = (char) inputStream.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;}

    /**
     * 向输入流中写命令
     * @param command 命令
     */
    public void write(String command){
        try {
            printStream.println(command);
            printStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行命令并返回执行结果
     * @param command 执行的命令
     * @return 返回的执行结果
     */
    public String sendCommand(String command){
        write(command);
        return read(prompt);
    }

    /**
     * 断开连接
     */
    public void disconnect(){
        try {
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return client.isConnected();
    }



}


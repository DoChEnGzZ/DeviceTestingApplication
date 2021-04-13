package com.example.devicetestingapplication;

import android.content.Context;
import android.util.Log;

import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.SocketException;
import java.net.UnknownHostException;


public class TFTPUtils implements Serializable {
    private static TFTPClient tftpClient=new TFTPClient();
    private LogIninfEntity logIninfEntity;
    private Context context;
    private static final int PORT=69;
    private static final int TransferMode= TFTP.BINARY_MODE;
    /**
     * 构造函数
     * @param context 上下文
     * @param logIninfEntity 登陆信息
     */
    public TFTPUtils(Context context, LogIninfEntity logIninfEntity) {
        this.logIninfEntity = logIninfEntity;
        this.context=context;
        tftpClient.setMaxTimeouts(10000);
    }

    /**
     * 发送文件
     * @param remotefilename 接收方的目录+文件名
     * @param localfilename 发送方的目录+文件名
     */
    public void SendFiles(String remotefilename,String localfilename){
        tftpClient.setDefaultTimeout(10000);
        try
        {
        InputStream input=new FileInputStream(localfilename);
            tftpClient.sendFile(remotefilename,TransferMode,input, logIninfEntity.getIpaddr(),PORT);
        }catch (FileNotFoundException e){
            Log.d("FILE error",e.getMessage());
        }catch (UnknownHostException e){
            Log.d("IP error",e.getMessage());
        }catch (IOException e)
        {
            Log.d("IO error",e.getMessage());
        }
    }

    /**
     * 接收文件
     * @param remotefilename 接收方的目录+文件名
     * @param localfilename 发送方的目录+文件名
     */
    public void DownloadFiles(String remotefilename,String localfilename){
        tftpClient.setDefaultTimeout(10000);
        FileOutputStream outputStream=null;
        File file=new File(localfilename);
        try {
            outputStream=new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Log.d("FILE error",e.getMessage());
        }
        if(file.exists())
        {
            Log.d("FILE error","the"+remotefilename+"is already exits");
            if(!file.delete())
            {
                Log.d("FILE error","cannot delete the file");
            }
        }
        try {
            tftpClient.receiveFile(remotefilename,TransferMode,outputStream, logIninfEntity.getIpaddr(),PORT);
        } catch (IOException e) {
            Log.d("IO error",e.getMessage());
        }

    }

    /**
     * 打开连接
     */
    public void open()
    {
        try{
            tftpClient.open();
        }catch (SocketException e){
            Log.d("socker error:",e.getMessage());
        }
    }


    /*
    关闭连接
     */
    public void close()
    {
        tftpClient.close();
    }

    /*
    返回是否已经建立好连接
     */
    public boolean isConnected()
    {
        return tftpClient.isOpen();
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//
//    }
//
//    public static final Parcelable.Creator<TFTPUtils> CREATOR=new Parcelable.Creator<TFTPUtils>(){
//        @Override
//        public TFTPUtils createFromParcel(Parcel source) {
//            TFTPUtils tftpUtils=new TFTPUtils();
//            return ;
//        }
//
//        @Override
//        public TFTPUtils[] newArray(int size) {
//            return new TFTPUtils[size];
//        }
//    };
}

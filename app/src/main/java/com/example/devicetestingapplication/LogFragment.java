package com.example.devicetestingapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Button button;
    private myApplication myapplication;
    private TelnetUtils telnetUtils;
    private Intent intent;
    private Toast toast;
    private LogSelecterAdapter adapter;
    private String log;
    public LogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogFragment newInstance(String param1, String param2) {
        LogFragment fragment = new LogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_log, container, false);
        myapplication=(myApplication)getActivity().getApplication();
        telnetUtils=myapplication.getTelnetUtils();
        if(!myapplication.isConnected())
        {
            toast.makeText(getContext(),"请先建立连接", Toast.LENGTH_LONG);
            intent=new Intent(getActivity(),ConnectActivity.class);
            startActivity(intent);
        }
        adapter=new LogSelecterAdapter();
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        RecyclerView recyclerView=view.findViewById(R.id.recyclerview_log);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        button=view.findViewById(R.id.btn_log_yes);
        setOnclicklistener();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setOnclicklistener(){
     button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Thread thread_getlog=new Thread(new Runnable() {
                 @Override
                 public void run() {
                     telnetUtils.sendCommand("cd u");
                     telnetUtils.sendCommand("kill -9 ./pardus");
                     log = telnetUtils.sendCommand("debug=" + adapter.getParm() + " ./pardus");
                 }
             });
             thread_getlog.start();
             try {
                 thread_getlog.join();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             String lod_dir= Environment.getExternalStorageDirectory().getAbsolutePath()+"/ftpboot/log.txt";
             File logfile=new File(lod_dir);
             if(logfile.exists())
             {
                 logfile.delete();
             }
             try {
                 logfile.createNewFile();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             try {
                 FileOutputStream outputStream=new FileOutputStream(logfile);
                 try {
                     outputStream.write(log.getBytes());
                     outputStream.flush();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             }
             toast.makeText(getActivity(),"日志生成成功！请选择其它应用打开",Toast.LENGTH_LONG).show();
             Uri log_content=FileProvider.getUriForFile(getContext(),
                     "com.example.devicetestingapplication.fileprovider",
                     logfile);
             Log.d("URI测试",log_content.toString());
             openFile(getContext(),log_content);
         }
//             telnetUtils.sendCommand("ftpput -u root -p 123456 -P 2021 "+myapplication.getIp()+"log.txt log.txt");
         });
     }

    public static void openFile(Context context, Uri file) {
        try {
            Intent intent = new Intent();
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                 intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(file, "text/plain");
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "没有能打开日志的软件", Toast.LENGTH_SHORT).show();
        }
    }



}

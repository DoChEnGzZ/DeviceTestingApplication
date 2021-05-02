package com.example.devicetestingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.SoftReference;

public class MenuActivity extends FragmentActivity implements View.OnClickListener, menuFragment.OnFragmentInteractionListener {


    private  menuFragment fragment_menu;
    private LogFragment fragment_log;
    private StatusFragment fragment_status;
    private SettingsFragment fragment_settings;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private TextView connect_menu;
    private TextView  status_menu;
    private TextView  log_menu;
    private TextView  device_menu;
    private final static int MENU=0;
    private final static int STATUS=1;
    private final static int LOG=2;
    private final static int SETTINGS=3;
    private final static String tag="menu test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_menu);
        } catch (Exception e) {
            Log.e("debug","onCreateView in menu",e);
            throw e;
        }
        initViews();
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        ShowFragment(MENU);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_tab_menu:
                Log.d(tag,"tab_menu clicked");
                ShowFragment(MENU);
                break;
            case R.id.tv_tab_status:
                Log.d(tag,"tab_menu clicked");
                ShowFragment(STATUS);
                break;
            case R.id.tv_tab_log:
                Log.d(tag,"tab_menu clicked");
                ShowFragment(LOG);
                break;
            case R.id.tv_tab_settings:
                Log.d(tag,"tab_menu clicked");
                ShowFragment(SETTINGS);
                break;
                default:
                    break;
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private <T extends View> T f(int ViewID)
    {
        return (T)findViewById(ViewID);
    }

    /**
     * 初始化views
     */
    private void initViews(){
        this.connect_menu=f(R.id.tv_tab_menu);
        this.status_menu=f(R.id.tv_tab_status);
        this.log_menu=f(R.id.tv_tab_log);
        this.device_menu=f(R.id.tv_tab_settings);
        connect_menu.setOnClickListener(this);
        status_menu.setOnClickListener(this);
        log_menu.setOnClickListener(this);
        device_menu.setOnClickListener(this);
    }

    /**
     * 将页面中的所有fragment隐藏
     * @param transaction fragment事务
     */
    private void HideFragments(FragmentTransaction transaction)
    {
        if(fragment_menu!=null)
            transaction.hide(fragment_menu);
        if(fragment_log!=null)
            transaction.hide(fragment_log);
        if(fragment_settings!=null)
            transaction.hide(fragment_settings);
        if(fragment_status!=null)
            transaction.hide(fragment_status);
    }

    private void ShowFragment(int ID)
    {
        transaction=manager.beginTransaction();
        HideFragments(transaction);
        switch (ID){
            case MENU:
                if(fragment_menu==null) {
                    fragment_menu = new menuFragment();
                    transaction.add(R.id.container, fragment_menu);
                    Log.d(tag,"tab_menu created");
                }
                else {
                    transaction.show(fragment_menu);
                    Log.d(tag,"tab_menu show");
                }
                break;
            case STATUS:
                if(fragment_status==null) {
                    Log.d(tag,"tab_status created");
                    fragment_status = new StatusFragment();
                    transaction.add(R.id.container,fragment_status);
                }
                else {
                    Log.d(tag,"tab_status show");
                    transaction.show(fragment_status);
                }
                break;
            case LOG:
                if(fragment_log==null) {
                    Log.d(tag,"tab_log created");
                    fragment_log = new LogFragment();
                    transaction.add(R.id.container, fragment_log);
                }
                else {
                    Log.d(tag,"tab_log show");
                    transaction.show(fragment_log);
                }
                break;
            case SETTINGS:
                if(fragment_settings==null) {
                    Log.d(tag,"tab_settings created");
                    fragment_settings = new SettingsFragment();
                    transaction.add(R.id.container, fragment_settings);
                }
                else
                    transaction.show(fragment_settings);
                Log.d(tag,"tab_menu show");
                break;
                default:
                    break;
        }
        transaction.commit();
    }


}

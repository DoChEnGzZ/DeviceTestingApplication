package com.example.devicetestingapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LogSelecterAdapter extends RecyclerView.Adapter<LogSelecterAdapter.LogSelecterVH> {
    @NonNull
    @Override
    public LogSelecterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.logselecter_item,parent,false);
        LogSelecterVH vh=new LogSelecterVH(view);
        return vh;
    }

    private String parm="0xffff";


    @Override
    public void onBindViewHolder(@NonNull LogSelecterVH holder, int position) {
        switch (position){
            case 0:
                holder.logselecter_title.setText("TCP");
                holder.logselecter_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b)
                        {
                            parm="0x0001";
                        }
                    }
                });
                break;
            case 1:
                holder.logselecter_title.setText("TCP_SDATA");
                holder.logselecter_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b)
                        {
                            parm="0x0002";
                        }
                    }
                });
                break;
            case 2:
                holder.logselecter_title.setText("TCP_RDATA");
                holder.logselecter_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b)
                        {
                            parm="0x0004";
                        }
                    }
                });
                break;
            case 3:
                holder.logselecter_title.setText("DATA_ERROR");
                holder.logselecter_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b)
                        {
                            parm="0x0010";
                        }
                    }
                });
                break;
            case 4:
                holder.logselecter_title.setText("SMC");
                holder.logselecter_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b)
                        {
                            parm="0x0020";
                        }
                    }
                });
                break;
            case 5:
                holder.logselecter_title.setText("BUTTON");
                holder.logselecter_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b)
                        {
                            parm="0x0040";
                        }
                    }
                });
                break;
            case 6:
                holder.logselecter_title.setText("PARSE");
                holder.logselecter_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b)
                        {
                            parm="0x0100";
                        }
                    }
                });
                break;
            case 7:
                holder.logselecter_title.setText("CHAN_RCV");
                holder.logselecter_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b)
                        {
                            parm="0x0200";
                        }
                    }
                });
                break;
            case 8:
                holder.logselecter_title.setText("CHAN_SEND");
                holder.logselecter_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b)
                        {
                            parm="0x0400";
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    static class LogSelecterVH extends RecyclerView.ViewHolder{
        TextView logselecter_title;
        Switch logselecter_switch;
        public LogSelecterVH(@NonNull View itemView) {
            super(itemView);
            logselecter_switch=(Switch) itemView.findViewById(R.id.logselecter_item_selecter);
            logselecter_title=(TextView)itemView.findViewById(R.id.logselecter_item_title);
        }
    }

    public String getParm() {
        return parm;
    }

    public void setParm(String parm) {
        this.parm = parm;
    }
}

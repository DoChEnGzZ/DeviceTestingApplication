package com.example.devicetestingapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.devicetestingapplication.Status.getVERSION;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusVH> {

    private Status status;

    static class StatusVH extends RecyclerView.ViewHolder{
        TextView tv_key;
        TextView tv_value;

        public StatusVH(@NonNull View itemView) {
            super(itemView);
            tv_key=(TextView)itemView.findViewById(R.id.tv_statusitem_key);
            tv_value=(TextView)itemView.findViewById(R.id.tv_statusitem_value);
        }
    }

    public StatusAdapter(Status status) {
        this.status = status;
    }

    @Override
    public void onBindViewHolder(@NonNull StatusVH holder, int position) {
        switch (position)
        {
            case 0:
                holder.tv_key.setText(status.getCpuTitle());
                holder.tv_value.setText(status.getCPUusage());
                break;
            case 1:
                holder.tv_key.setText(status.getMemoryTitle());
                holder.tv_value.setText(status.getMemoryusage());
                break;
            case 2:
                holder.tv_key.setText(status.getDiskTitle());
                holder.tv_value.setText(status.getDiskusage());
                break;
            case 3:
                holder.tv_key.setText(status.getVERSION());
                holder.tv_value.setText(String.valueOf(status.getVersion()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return status.getSize();
    }

    @NonNull
    @Override
    public StatusVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.status_item,parent,false);
        StatusVH vh=new StatusVH(view);
        return vh;
    }
}

package com.example.lc.materialuitest.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lc.materialuitest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BluetoothDeviceAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<BluetoothDevice> list;

    OnItemClickListener onItemClickListener = null;

    public BluetoothDeviceAdapter(Context context, List<BluetoothDevice> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BluetoothDeviceViewHolder(LayoutInflater.from(context).inflate(R.layout.item_blue_tooth_device, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        BluetoothDeviceViewHolder holder = (BluetoothDeviceViewHolder) viewHolder;

        BluetoothDevice device = list.get(i);
        if (!TextUtils.isEmpty(device.getName())) {
            holder.tvName.setText(device.getName());
        }else {
            holder.tvName.setText("未知设备");
        }
        holder.tvAddress.setText(device.getAddress());

        if (device.getBondState() == BluetoothDevice.BOND_BONDED){
            holder.tvState.setText("Ready");
            holder.tvState.setTextColor(Color.GREEN);
        } else {
            holder.tvState.setText("New");
            holder.tvState.setTextColor(Color.RED);
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BluetoothDeviceViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.rl_item_bluetooth_device_item)
        RelativeLayout item;
        @BindView(R.id.tv_item_bluetooth_device_name)
        TextView tvName;
        @BindView(R.id.tv_item_bluetooth_device_address)
        TextView tvAddress;
        @BindView(R.id.tv_item_bluetooth_device_state)
        TextView tvState;

        public BluetoothDeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

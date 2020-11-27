package com.example.eranp.clientpage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Eran P on 04/02/2018.
 */

public class DeviceAdapter extends ArrayAdapter<Device> {
    private Activity context;
    List<Device> devices;

    public DeviceAdapter(Activity context, List<Device> devices) {
        super(context, R.layout.device_row, devices);
        this.context = context;
        this.devices = devices;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.device_row, null, true);

        TextView textViewNameDevice = (TextView) listViewItem.findViewById(R.id.modelDevice_TV1);
        TextView textViewModelDevice = (TextView) listViewItem.findViewById(R.id.nameDevice_TV1);

        Device device = devices.get(position);
        textViewNameDevice.setText(device.getDeviceName());
        textViewModelDevice.setText(device.getDeviceModel());


        return listViewItem;

    }
}

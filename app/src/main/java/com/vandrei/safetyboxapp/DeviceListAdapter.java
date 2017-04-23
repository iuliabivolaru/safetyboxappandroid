package com.vandrei.safetyboxapp;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by VAndrei on 4/23/2017.
 */

public class DeviceListAdapter extends ArrayAdapter<Device> {


    private boolean showDistance = false;

    public void setShowDistance(boolean showDistance) {
        this.showDistance = showDistance;
    }

    public DeviceListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public DeviceListAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }




    public DeviceListAdapter(@NonNull Context context, @LayoutRes int resource, List<Device> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.devicelistitem, null);
        }

        Device device = getItem(position);

        TextView textView = (TextView) v.findViewById(R.id.deviceName);
        textView.setText(device.getName());

        if(showDistance){
            TextView distance = (TextView) v.findViewById(R.id.distance);
            distance.setVisibility(View.VISIBLE);
            distance.setText(device.getDistanceDb()+"db");
        }

        return v;

    }
}

package com.vandrei.safetyboxapp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MonitorDevices extends AppCompatActivity {


    private static final int REQUEST_ENABLE_BT = 1;
    private TextView deviceName;

    private ListView deviceList;
    private HashMap<String, Device> scanResults;

    String registeredDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_devices);

        Intent intent = getIntent();
        registeredDevice = intent.getStringExtra("registeredDevice");

        scanResults = new HashMap<>();

        deviceList = (ListView) findViewById(R.id.list);

        BluetoothManager btManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);

        BluetoothAdapter btAdapter = btManager.getAdapter();
        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent,REQUEST_ENABLE_BT);
        }

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                3);

        BluetoothLeScanner myScanner = btAdapter.getBluetoothLeScanner();
        myScanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                Device device = new Device(result.getScanRecord().getDeviceName(), result.getRssi(), result.getDevice().getAddress());
                if(device.getName()!=null && device.getUuid().equals(registeredDevice)) scanResults.put(result.getDevice().getAddress(),device);
                displayMonitoredDevices();

            }
        });



    }

    private void displayMonitoredDevices() {
        ArrayList<Device> devices = new ArrayList<>();

        Device ourDevice = scanResults.get(registeredDevice);
        if(ourDevice!=null) devices.add(ourDevice);
        DeviceListAdapter adapter = new DeviceListAdapter(this,android.R.layout.simple_list_item_1, devices);

        adapter.setShowDistance(true);

        deviceList.setAdapter(adapter);

    }
}

package com.vandrei.safetyboxapp;

import android.Manifest;
import android.app.Activity;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class SearchDevicesActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private TextView deviceName;

    private ListView deviceList;
    private HashMap<String, Device> scanResults;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_devices);

        setTitle("Add an asset");

        scanResults = new HashMap<>();

        deviceName = (TextView) findViewById(R.id.deviceName);
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
                if(device.getName()!=null) scanResults.put(result.getDevice().getAddress(),device);
                displayFoundDevice();

            }
        });
    }

    private void displayFoundDevice() {
        ArrayList<Device> devices = new ArrayList<>();

        Device ourDevice = scanResults.get("00:00:00:11:11:11");
        if(ourDevice!=null) devices.add(ourDevice);



        ArrayAdapter adapter = new DeviceListAdapter(this,android.R.layout.simple_list_item_1, devices);
        deviceList.setAdapter(adapter);
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",scanResults);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}

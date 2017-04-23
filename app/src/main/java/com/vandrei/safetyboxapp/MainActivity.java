package com.vandrei.safetyboxapp;

import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int SEARCH_DEVICES_INTENT = 635;

    private ScanResult scanResult;
    private int dblevel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button search = (Button) findViewById(R.id.addDevice);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchForDevices();
            }
        });

        Button monitor = (Button) findViewById(R.id.listDevices);
        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monitorDevices();
            }
        });


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.range5m:
                if (checked) dblevel = -76;
                    break;
            case R.id.range10m:
                if (checked) dblevel = -86;
                    break;
            case R.id.range30m:
                if (checked) dblevel = -100;
                break;
            default:
                dblevel = -40;
                break;
        }
    }

    private void monitorDevices() {
        Intent intent = new Intent(this, MonitorDevices.class);
        Bundle bundle = new Bundle();
        intent.putExtra("registeredDevice", "00:00:00:11:11:11");
        intent.putExtra("rangeDb",dblevel);
        startActivity(intent);

    }


    public void SearchForDevices() {
        Intent intent = new Intent(this, SearchDevicesActivity.class);
        startActivityForResult(intent, SEARCH_DEVICES_INTENT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case SEARCH_DEVICES_INTENT:
                if(resultCode==RESULT_OK){
                    Toast.makeText(this,"Your asset is now monitored!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Could not add asset",Toast.LENGTH_SHORT).show();
                }


                break;
            default:
                break;
        }
    }
}

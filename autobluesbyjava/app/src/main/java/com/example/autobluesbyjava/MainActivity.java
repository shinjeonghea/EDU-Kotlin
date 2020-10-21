package com.example.autobluesbyjava;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    BluetoothAdapter myBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
    String Address = "00:19:12:BC:68:FC";
    Set<BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();
    BluetoothSocket m_bluetoothSocket = null;
    UUID m_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!myBluetoothAdapter.isEnabled()){
            Toast.makeText(getApplicationContext(), "블루투스를 켜 주세요.", Toast.LENGTH_SHORT).show();
        }
        BluetoothDevice reuslt = myBluetoothAdapter.getRemoteDevice(Address);
            try {
                 m_bluetoothSocket = reuslt.createInsecureRfcommSocketToServiceRecord(m_UUID);
                 BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                 m_bluetoothSocket.connect();
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
    }
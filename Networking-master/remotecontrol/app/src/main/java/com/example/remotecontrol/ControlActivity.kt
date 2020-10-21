package com.example.remotecontrol

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.control_layout.*
import java.io.IOException
import java.util.*
class ControlActivity: AppCompatActivity() {
    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress:ProgressDialog
        lateinit var m_bluetoothAdapter:BluetoothAdapter
        var m_isConnected:Boolean = false
        lateinit var m_address:String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.control_layout)
        m_address=intent.getStringExtra(MainActivity.EXTRA_ADDRESS)
        ConnectToDevice(this).execute()
        /*wind_off.setOnClickListener{ sendCommand("off") }
        wind_low.setOnClickListener{ sendCommand("on1") }
        wind_mid.setOnClickListener { sendCommand("on2") }
        wind_high.setOnClickListener { sendCommand("on3") }
        rotation_off.setOnClickListener { sendCommand("off4") }
        angle_60.setOnClickListener { sendCommand("on4") }
        angle_120.setOnClickListener { sendCommand("on5") }
        angle_180.setOnClickListener { sendCommand("on6") }
        disconnect_device.setOnClickListener{ disconnect() }*/
    }
    private fun sendCommand(input:String) {
        if(m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            }
            catch (e:IOException) {
                e.printStackTrace()
            }
        }
    }
    private fun disconnect() {
        if(m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnected = false
            }
            catch (e:IOException) {
                e.printStackTrace()
            }
        }
        finish()
    }
    private class ConnectToDevice(c: Context): AsyncTask<Void, Void, String>() {
        private var connectSuccess:Boolean = true
        private val context:Context
        init {
            this.context=c
        }
        override fun onPreExecute() {
            super.onPreExecute()
            m_progress= ProgressDialog.show(context,"Connecting...","please wait")
        }
        override fun doInBackground(vararg params: Void?): String? {
            try {
                if(m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device:BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()
                }
            }
            catch(e:IOException) {
                connectSuccess=false
                e.printStackTrace()
            }
            return null
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(!connectSuccess) {
                Log.i("data","coudln't connect")
            }
            else {
                m_isConnected=true
            }
            m_progress.dismiss()
        }
    }
}

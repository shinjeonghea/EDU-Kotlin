package com.example.kotlinblue

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_blue.*
import kotlinx.android.synthetic.main.activity_main.*

class Bluetooth  : AppCompatActivity() {
    private var m_bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var m_pairedDevices:Set<BluetoothDevice>
    private val REQUEST_ENABLE_BLUETOOTH = 1
    private val scan_deviceList : ArrayList<BluetoothDevice> = ArrayList()
    private val scan_nameList : ArrayList<String> = ArrayList()

    companion object {
        val EXTRA_ADDRESS: String = "Device_address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blue)
        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        // val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        //registerReceiver(m_receiver, filter)
        if(m_bluetoothAdapter == null) {
            Toast.makeText(this,"This device dosen't support bluetooth", Toast.LENGTH_SHORT).show()
        }
        else {
            if (!m_bluetoothAdapter!!.isEnabled) {
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
            }
            pairedDeviceList()
            scan_device_refresh.setOnClickListener{ scanDevicedList()  }
        }
    }


     private fun scanDevicedList(){
        scan_deviceList.clear()
        scan_nameList.clear()
        //코틀린에서 ?.연산자는 앞의 값이 nul일 경우 null을 리턴하고 아닐경우 . 뒤의 명령어 실행
        // !! 연산자는 명령어가 null일 경우 npe던짐
        if(m_bluetoothAdapter?.isDiscovering()!!){
            m_bluetoothAdapter!!.cancelDiscovery()
            Toast.makeText(this,"ummmmmmmmmmmmmmmmmmmmmmmm", Toast.LENGTH_SHORT).show()

        }


         m_bluetoothAdapter!!.startDiscovery()

         val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
         registerReceiver(m_receiver, filter)

         Toast.makeText(this,"rodakfjd;fja;djflajds;fja;dksf", Toast.LENGTH_SHORT).show()

         val adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1,scan_nameList)

         scan_device_list.adapter = adapter1
         scan_device_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val device: BluetoothDevice = scan_deviceList[position]
            val address: String = device.address

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(EXTRA_ADDRESS,address)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }


     }

    private fun pairedDeviceList() {
        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        val deviceList : ArrayList<BluetoothDevice> = ArrayList()
        val nameList : ArrayList<String> = ArrayList()
        if(!m_pairedDevices.isEmpty()) {
            for(device: BluetoothDevice in m_pairedDevices) {
                deviceList.add(device)
                nameList.add(device.name+"("+device.address+")")
                Log.i("device",""+device.name)
            }
        }
        else {
            Toast.makeText(this,"Paired devices not found", Toast.LENGTH_SHORT).show()
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,nameList)
        select_device_list.adapter = adapter
        select_device_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val device: BluetoothDevice = deviceList[position]
            val address: String = device.address

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(EXTRA_ADDRESS,address)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if(resultCode == Activity.RESULT_OK) {
                if(m_bluetoothAdapter!!.isEnabled) {
                    Toast.makeText(this,"Bluetooth has been enabled", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this,"Bluetooth has benn disabled", Toast.LENGTH_SHORT).show()

                }
            }
            else if(resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this,"Bluetooth enabling has benn canceled", Toast.LENGTH_SHORT).show()

            }
        }
    }
    private val m_receiver = object : BroadcastReceiver() {
         override fun onReceive(context: Context, intent: Intent) {
             val action : String? = intent.action
             when(action) {
                 BluetoothDevice.ACTION_FOUND -> {
                     // Discovery has found a device. Get the BluetoothDevice
                     // object and its info from the Intent.
                     Log.i("data", "fukkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk")
                     val device: BluetoothDevice =
                         intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                     scan_deviceList.add(device)
                     scan_nameList.add(device.name+"("+device.address+")")
                     //  adapter1.notifyDataSetChanged()
                 }
             }
         }
     }
     override fun onDestroy() {
    super.onDestroy()
    // Don't forget to unregister the ACTION_FOUND receiver.
    unregisterReceiver(m_receiver)
    }


}
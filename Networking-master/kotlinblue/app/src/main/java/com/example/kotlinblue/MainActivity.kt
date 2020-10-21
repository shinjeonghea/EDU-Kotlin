package com.example.kotlinblue

import android.app.Activity
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.InputStream
import java.util.*

class MainActivity:AppCompatActivity() {

    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") //블루투스를 찾는 고유 식별자
        var m_bluetoothSocket: BluetoothSocket? = null //데이터를 주고받기 위한 소켓 객체
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_address: String
    }
    private val REQUEST_DEVICE =384


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetooth_btn.setOnClickListener {
            val intent = Intent(this, Bluetooth::class.java)
            startActivityForResult(intent, REQUEST_DEVICE)
        }

        button.setOnClickListener{
            sendmessage("a")
        }

       /* button2.setOnClickListener{
            readmessage()
        }*/

    }

   /* private fun readmessage() {
        val mInputstream:InputStream = m_bluetoothSocket!!.inputStream
        val mmbuffer: ByteArray = ByteArray(1024)
        textView.setText(mInputstream.read(mmbuffer))

    }*/

    private fun sendmessage(input: String) {
        if(m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            }
            catch (e:IOException) {
                e.printStackTrace()
            }
        }
    }






    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQUEST_DEVICE -> {
                if (resultCode == Activity.RESULT_OK) {
                    m_address = data!!.getStringExtra(Bluetooth.EXTRA_ADDRESS)
                    ConnectToDevice(this).execute()
                }
            }
        }
    }

    //

    /*private fun sendCommand(input: String) {
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()S
            }
        }
    }*/


   private fun disconnect() {
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.close() //!!연산자는 NULL이 될수 없다는 것을 단언하는 연산자
                m_bluetoothSocket = null
                m_isConnected = false
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        finish()
    }
   // https://brunch.co.kr/@mystoryg/84 - 핸들러에 대한 설명
 //https://itmining.tistory.com/7
    //AsyncTask == thread + handler
    inner class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {
        private var connectSuccess: Boolean = true
        private val context: Context

        init {
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
            Log.i("data", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if (m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()
                    var a :String = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                    Toast.makeText(this.context,a, Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                connectSuccess = false
                var a :String = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                Toast.makeText(this.context,a, Toast.LENGTH_SHORT).show()
                e.printStackTrace()

            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!connectSuccess) {
                Log.i("data", "couldn't connect")
                var a :String = "can' find device"
                Toast.makeText(this.context,a, Toast.LENGTH_SHORT).show()
            } else {
                Log.i("data", "connect")
                m_isConnected = true
            }
            m_progress.dismiss()
        }
    }

}

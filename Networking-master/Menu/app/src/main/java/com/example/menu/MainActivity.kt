package com.example.menu

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerForContextMenu(button2)
        registerForContextMenu(baseLayout)
        val btnScaleX = button2.scaleX
        val btnScaleY = button2.scaleY
        button2.setOnClickListener {
            button2.scaleX = btnScaleX
            button2.scaleY = btnScaleY
            button2.rotation = 0f
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val mInflater = getMenuInflater()
        mInflater.inflate(R.menu.menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemRed -> {
                baseLayout.setBackgroundColor((Color.RED))
                return true
            }
            R.id.itemGreen -> {
                baseLayout.setBackgroundColor((Color.GREEN))
                return true
            }
            R.id.itemBlue -> {
                baseLayout.setBackgroundColor((Color.BLUE))
                return true
            }

            R.id.subRotate -> {
                button2.rotation = button2.rotation + 45F
                return true
            }
            R.id.subSize -> {
                button2.scaleX = button2.scaleX * 2F
                button2.scaleY = button2.scaleY * 2F
                return true
            }
            R.id.end -> {
                finish()
            }
        }
        return false
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val mInflater = menuInflater
        when (v) {
            button2 -> {
                menu!!.setHeaderTitle("배경 모양 변경")
                mInflater.inflate(R.menu.menu1, menu)
            }
            baseLayout ->{
                mInflater.inflate(R.menu.menu2,menu)
            }
            /*button2 -> {
                menu!!.setHeaderTitle("배경 모양 변경")
                mInflater.inflate(R.menu.menu2, menu)
            }*/
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        //return super.onContextItemSelected(item)
   // (item: MenuItem): Boolean {
        when(item.itemId){

            R.id.subRotate -> {
                button2.rotation = button2.rotation + 45F
                return true
            }
            R.id.subSize -> {
                button2.scaleX = button2.scaleX * 2F
                button2.scaleY = button2.scaleY * 2F
                return true
            }
            R.id.end -> {
                finish()
                return true
            }
        }
        return false
    }
}
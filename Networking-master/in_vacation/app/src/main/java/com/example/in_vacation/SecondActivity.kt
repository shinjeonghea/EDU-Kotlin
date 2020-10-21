package com.example.in_vacation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        checkbox1.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                check.visibility = View.VISIBLE
            }else{
                check.visibility = View.INVISIBLE
            }
        }
        group.setOnCheckedChangeListener { group, checkedId ->
           when(checkedId){
               R.id.dog ->{
                   image.visibility = View.VISIBLE
                   image.setImageResource(R.drawable.dog)
               }
               R.id.cat ->{
                   image.visibility = View.VISIBLE
                   image.setImageResource(R.drawable.cat)
               }

               R.id.rab ->{
                   image.visibility = View.VISIBLE
                   image.setImageResource(R.drawable.rab)
               }
           }
        }
        btn.setOnClickListener{
            checkbox1.isChecked = false
        }
    }
}
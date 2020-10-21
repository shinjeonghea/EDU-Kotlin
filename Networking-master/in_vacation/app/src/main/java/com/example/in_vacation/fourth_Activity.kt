package com.example.in_vacation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_fourth_.*

class fourth_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth_)

        val intent = Intent(this, forth_Activity_2::class.java)
        val intent2 = Intent(this, fourth_Activity_3::class.java)


        act.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.second -> {
                    open.setOnClickListener {
                        startActivity(intent)
                    }
                }
                R.id.third -> {
                    open.setOnClickListener {
                        startActivity(intent2)
                    }
                }

            }

        }
    }
}
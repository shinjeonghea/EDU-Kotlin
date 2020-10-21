package com.example.in_vacation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { //null 포인트를 혐오해서 중간중간 표시하는데 이게 ? 임
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // 이거 해석하면 정수나옴


        add.setOnClickListener{
            var a = editText1.text.toString()
            var b = editText2.text.toString()
            textView2.text = (a.toInt()+b.toInt()).toString()
        }

        sub.setOnClickListener{
            var a = editText1.text.toString()
            var b = editText2.text.toString()
            textView2.text = (a.toInt()-b.toInt()).toString()
        }

        mul.setOnClickListener{
            var a = editText1.text.toString()
            var b = editText2.text.toString()
            textView2.text = (a.toInt()*b.toInt()).toString()
        }

        div.setOnClickListener{
            var a = editText1.text.toString()
            var b = editText2.text.toString()
            textView2.text = (a.toDouble()/b.toDouble()).toString()
        }
    }
}
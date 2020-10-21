package com.example.in_vacation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.add
import kotlinx.android.synthetic.main.activity_main.div
import kotlinx.android.synthetic.main.activity_main.editText1
import kotlinx.android.synthetic.main.activity_main.editText2
import kotlinx.android.synthetic.main.activity_main.mul
import kotlinx.android.synthetic.main.activity_main.sub
import kotlinx.android.synthetic.main.activity_main.textView2
import kotlinx.android.synthetic.main.activity_third_.*

class third_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { //null 포인트를 혐오해서 중간중간 표시하는데 이게 ? 임
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_) // 이거 해석하면 정수나옴


        btn0.setOnClickListener{
            if(editText1.isFocused==true){
             editText1.text = editText1.getText().append("0")
            }
            else if(editText2.isFocused==true){
                editText2.text = editText2.getText().append("0")
            }
        }
        btn1.setOnClickListener{
            if(editText1.isFocused==true){
                editText1.text = editText1.getText().append("1")
            }
            else if(editText2.isFocused==true){
                editText2.text = editText2.getText().append("1")
            }
        }
        btn2.setOnClickListener{
            if(editText1.isFocused==true){
                editText1.text = editText1.getText().append("2")
            }
            else if(editText2.isFocused==true){
                editText2.text = editText2.getText().append("2")
            }
        }
        btn3.setOnClickListener{
            if(editText1.isFocused==true){
                editText1.text = editText1.getText().append("3")
            }
            else if(editText2.isFocused==true){
                editText2.text = editText2.getText().append("3")
            }
        }
        btn4.setOnClickListener{
            if(editText1.isFocused==true){
                editText1.text = editText1.getText().append("4")
            }
            else if(editText2.isFocused==true){
                editText2.text = editText2.getText().append("4")
            }
        }


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
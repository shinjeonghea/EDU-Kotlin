package com.example.dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog1.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button1.setOnClickListener {
            val dlgView = layoutInflater.inflate(R.layout.dialog, null)
            val dlgBuilder = AlertDialog.Builder(this)
            val etName = dlgView.findViewById<EditText>(R.id.dlgEdt1)
            val etEmail = dlgView.findViewById<EditText>(R.id.dlgEdt2)
            dlgBuilder.setTitle("사용자 정보 입력")
            dlgBuilder.setIcon(R.drawable.ic_menu_allfriends)
            dlgBuilder.setView(dlgView)
            dlgBuilder.setPositiveButton("확인") { dialogInterface, i ->
                tvName.text = etName.text.toString()
                tvEmail.text = etEmail.text.toString()
            }.setNegativeButton("취소") { dialogInterface, i ->
                val toast = Toast(this)
                val toastView = layoutInflater.inflate(R.layout.toast, null);
                val toastTextView = toastView.findViewById<TextView>(R.id.toastText)
                toastTextView.text = "취소했습니다."
                toast.setView(toastView)
                toast.show()
            }.show()
        }
        button2.setOnClickListener {
            val dlgView1 = layoutInflater.inflate(R.layout.dialog1, null)
            val dlgBuilder1 = AlertDialog.Builder(this)
            val etName = dlgView1.findViewById<TextView>(R.id.dlgEdt3)
            val etEmail = dlgView1.findViewById<TextView>(R.id.dlgEdt4)
            etName.setText(Name.text.toString())
            etEmail.setText(Email.text.toString())
            dlgBuilder1.setTitle("사용자 정보 입력")
            dlgBuilder1.setIcon(R.drawable.ic_menu_allfriends)
            dlgBuilder1.setView(dlgView1)
            dlgBuilder1.setPositiveButton("확인") { dialogInterface, i ->
                val toast = Toast(this)
                val toastView = layoutInflater.inflate(R.layout.toast, null);
                val toastTextView = toastView.findViewById<TextView>(R.id.toastText)
                toastTextView.text = "확인했습니다."
                toast.setView(toastView)
                toast.show()
            }.setNegativeButton("취소") { dialogInterface, i ->
                val toast = Toast(this)
                val toastView = layoutInflater.inflate(R.layout.toast, null);
                val toastTextView = toastView.findViewById<TextView>(R.id.toastText)
                toastTextView.text = "취소했습니다."
                toast.setView(toastView)
                toast.show()
            }.show()
        }
    }
}
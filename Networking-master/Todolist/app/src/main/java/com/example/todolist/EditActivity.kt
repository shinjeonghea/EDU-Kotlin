package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import java.util.*
import io.realm.kotlin.createObject
import kotlinx.android.synthetic.main.activity_edit.*


class EditActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

    }
    val realm = Realm.getDefaultInstance()
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
    //
    val calendar: Calendar = Calendar.getInstance()//날짜를 다루는 캘린더 객체
    private fun insertTodo() {

        realm.beginTransaction() //트렌젝션 시작
        val todo = realm.createObject<Todo>(nextId()) //새 객체 생성
        todo.title = todoEditText.text.toString() //값 설정
        todo.date = calendar.timeInMillis
        realm.commitTransaction() //트랜젝션 종료
        alert("내용이 추가되었습니다."){ yesButton { finish() } }.show()//다이얼로그 표시
    }
    private fun nextId(): Int { //다음 id를 반환
        val maxId = realm.where<Todo>().max("id")
        if (maxId != null) {
            return maxId.toInt() + 1
        }
        return 0
    }
}
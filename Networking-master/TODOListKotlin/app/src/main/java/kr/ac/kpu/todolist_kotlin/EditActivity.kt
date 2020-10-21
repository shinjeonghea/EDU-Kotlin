package kr.ac.kpu.todolist_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.util.*

class EditActivity : AppCompatActivity() {
    var realm = Realm.getDefaultInstance()

    private fun insertMode() {
        deleteFab.visibility = View.GONE
        doneFab.setOnClickListener {
            insertTodo()
        }
    }


    private fun updateMode(id: Long) {

        val todo = realm.where<Todo>().equalTo("id", id).findFirst()!!
        todoEditText.setText(todo.name)
        todoEditText2.setText(todo.num)
        todoEditText3.setText(todo.deliver)
        calendarView.date = todo.date
        doneFab.setOnClickListener {
            updateTodo(id)
        }
// 삭제 버튼을 클릭하면 삭제
        deleteFab.setOnClickListener {
            deleteTodo(id)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    val calendar: Calendar = Calendar.getInstance()//날짜를 다루는 캘린더 객체
    private fun insertTodo() {
        realm.beginTransaction() //트렌젝션 시작
        val todo = realm.createObject<Todo>(nextId()) //새 객체 생성
        todo.date = calendar.timeInMillis
        todo.title = "장비명 : ${todoEditText.text.toString()}\n" +
                "개수 : ${todoEditText2.text.toString()}\n" +
                "배송지 : ${todoEditText3.text.toString()}"

        todo.name = todoEditText.text.toString()
        todo.num = todoEditText2.text.toString()
        todo.deliver = todoEditText3.text.toString()

        realm.commitTransaction() //트랜젝션 종료
        alert("내용이 추가되었습니다."){ yesButton { finish() } }.show()//다이얼로그 표시
    }

    private fun nextId(): Int{
        val maxId = realm.where<Todo>().max("id")
        if(maxId != null){
            return maxId.toInt() + 1
        }
        return 0
    }

    private fun updateTodo(id: Long) {
        realm.beginTransaction() //트렌젝션 시작
        val todo = realm.where<Todo>().equalTo("id", id).findFirst()!!//!!:todo는 이후부터 null이 아님
        todo.date = calendar.timeInMillis
        todo.title = "장비명 : ${todoEditText.text.toString()}\n" +
                "개수 : ${todoEditText2.text.toString()}\n" +
                "배송지 : ${todoEditText3.text.toString()}"

        todo.name = todoEditText.text.toString()
        todo.num = todoEditText2.text.toString()
        todo.deliver = todoEditText3.text.toString()

        realm.commitTransaction() //트렌젝션 종료 반영
        alert("내용이 변경되었습니다.") { //다이얼로그 표시
            yesButton { finish() }
        }.show()
    }

    private fun deleteTodo(id: Long) {
        realm.beginTransaction()
        val todo = realm.where<Todo>().equalTo("id", id).findFirst()!!
        todo.deleteFromRealm() // deleteFromRealm 메서드로 삭제
        realm.commitTransaction()
        alert("내용이 삭제되었습니다.") {
            yesButton { finish() }
        }.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val id = intent.getLongExtra("id",-1L)//1:Int, 1L:Long, 1.0:Double, 1.0f:Float
        if (id == -1L) { //초기화 값(기본값)인 -1이 그대로 넘어오면 추가모드
            insertMode()
        } else { //중간에 변경되었으면 수정 모드
            updateMode(id)
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
    }
}

package kr.ac.kpu.todolist_kotlin

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Todo(@PrimaryKey var id: Long = 0,
                var title: String = "",
                var name: String = "",
                var num: String = "",
                var deliver: String = "",
                var date: Long = 0) : RealmObject(){
}
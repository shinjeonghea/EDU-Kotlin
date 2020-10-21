package com.example.in_vacation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_reference_result.*

class ReferenceResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reference_result)
        setTitle("투표 결과")
        var intent = getIntent()
        val bundle = intent.getBundleExtra("bundle") ?: null
        val imageName = bundle?.getSerializable("imageName") as? ArrayList<String>
        val voteResult = bundle?.getSerializable("VoteCount") as? ArrayList<Int>
        val array = arrayOfNulls<Int>(9)

        var imgID = arrayListOf(R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,R.drawable.pic7,R.drawable.pic9 )
        var textView = arrayListOf<TextView>(tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9)
        val ratingBar =
            arrayListOf<RatingBar>(rbar1, rbar2, rbar3, rbar4, rbar5, rbar6, rbar7, rbar8, rbar9)
        var i = 0
        var j = 0
        var max = 0
        var maxindex = 0
        textView.forEach { tv ->
            tv.text = imageName?.get(i)
            i++
        }
        ratingBar.forEach { rbar ->
            rbar.rating = voteResult?.get(j)?.toFloat()!!
            j++
        }
        for(i in 0..8){
            array[i] = voteResult?.get(i)?.toInt()
        }
        for(i in 0..8){
            if(max< array[i]!!){
               maxindex = i
            }
        }
        maxindex++
        val resId = this.resources.getIdentifier("pic$maxindex","drawable", this.packageName)
        imgview.setImageResource(resId)

        btnReturn.setOnClickListener {
            finish()
        }
    }
}
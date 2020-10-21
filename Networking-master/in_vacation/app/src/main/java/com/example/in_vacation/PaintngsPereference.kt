package com.example.in_vacation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_paintngs_pereference.*

class PaintngsPereference : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paintngs_pereference)

        val intent = Intent(this, ReferenceResult::class.java)

        setTitle("명화 선호도 투표")
        var voteCount = arrayListOf<Int>(0, 0, 0, 0, 0, 0, 0, 0, 0)
        val imageId = arrayListOf<ImageView>(iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9)
        val imageName = arrayListOf<String>("독서하는 소녀", "꽃장식 모자 소녀", "부채를 든 소녀", "이레느깡 단 베르양", " 잠자는 소녀", "테라스의 두 자매", "피아노 레슨", "피아노 앞의 소녀들", "해변에서")

        var i = 0
        for (imageView in imageId) {
            var j = i
            imageView.setOnClickListener {
                voteCount[j]++
                Toast.makeText(this, "${imageName[j]} : 총 ${voteCount[j]}표", Toast.LENGTH_SHORT)
                    .show()
            }
            i++
        }
        val bundle = Bundle()
        bundle.putSerializable("imageName", imageName)
        bundle.putSerializable("VoteCount", voteCount)
        btnResult.setOnClickListener {
            intent.putExtra("bundle", bundle)
            startActivity(intent)
        }
    }
}
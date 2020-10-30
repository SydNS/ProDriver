package com.example.prodriver.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.driversapp.Controllers.InfoRecyclerAdapter
import com.example.driversapp.Models.InfoData
import com.example.prodriver.R
import kotlinx.android.synthetic.main.activity_info.*

class Info : AppCompatActivity() {
    private var news =ArrayList<InfoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val infoData= InfoData(
            "Dean",
            "It hurts. Loving you the way I do. It hurts. When all that's left to do is watch it burn. Oh",
            "20 jan 2016",
            "Sande"
        )
        var x=0
        while (x<100){
            news.add(infoData)
            x++
        }

        newslist.adapter=
            InfoRecyclerAdapter(news)
        val layoutManager = LinearLayoutManager(this)
        newslist.layoutManager = layoutManager
    }
}
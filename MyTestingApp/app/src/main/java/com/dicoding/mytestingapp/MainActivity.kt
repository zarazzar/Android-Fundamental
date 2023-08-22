package com.dicoding.mytestingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //    private var btnSetValue: Button? = null
    private lateinit var btnSetValue: Button
    private lateinit var tvText: TextView

    private var names = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvText = findViewById(R.id.tv_text)
//        btnSetValue!!.setOnClickListener(this)
        btnSetValue = findViewById(R.id.btn_set_value)

        btnSetValue.setOnClickListener(this)

        names.add("Azzar")
        names.add("Rizky")
        names.add("Lidya")

    }

    override fun onClick(v: View) {
//
        if (v.id == R.id.btn_set_value) {
            Log.d("mainActivity", names.toString()) //logd
            val name = StringBuilder()
//            for (i in 0..3) {
//            for (i in 0 until (names.size)) {
            for (i in 0 .. 2){
                name.append(names[i]).append("\n")
            }
            tvText.text = name.toString()
        }
    }
}
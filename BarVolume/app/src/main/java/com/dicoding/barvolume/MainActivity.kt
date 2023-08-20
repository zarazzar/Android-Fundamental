package com.dicoding.barvolume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etWidth : EditText
    private lateinit var etHeight : EditText
    private lateinit var etLength : EditText
    private lateinit var btnCalulate : Button
    private lateinit var tvResult : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etWidth = findViewById(R.id.et_width)
        etHeight = findViewById(R.id.et_height)
        etLength = findViewById(R.id.et_length)
        btnCalulate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)

        btnCalulate.setOnClickListener(this)

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT)
            tvResult.text = result
        }

    }

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, tvResult.text.toString())

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_calculate) {
            val inputLength = etLength.text.toString().trim()
            val inputWidth = etWidth.text.toString().trim()
            val inputHeight = etHeight.text.toString().trim()

            var isEmpty = false

            if (inputLength.isEmpty()) {
                isEmpty = true
                etLength.error = "Field Ini Tidak Boleh Kosong"
            }
            if (inputWidth.isEmpty()) {
                isEmpty = true
                etWidth.error = "Field Ini Tidak Boleh Kosong"
            }
            if (inputHeight.isEmpty()) {
                isEmpty = true
                etHeight.error = "Field Ini Tidak Boleh Kosong"
            }

            if (!isEmpty) {
                val barVolume =
                    inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
                tvResult.text = barVolume.toString()
            }

        }
    }
}
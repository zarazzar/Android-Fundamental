package com.dicoding.myintentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button



class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMoveActivity: Button = findViewById(R.id.btn_moveActivity)

        btnMoveActivity.setOnClickListener(this)

        val btnMoveWithDataActivity: Button = findViewById(R.id.btn_moveWithDataActivity)
        btnMoveWithDataActivity.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_moveActivity ->{
                val moveIntent = Intent(this@MainActivity,MoveActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.btn_moveWithDataActivity -> {
                val Intent = Intent(this@MainActivity, MoveWithDataActivity::class.java)
                Intent.putExtra(MoveWithDataActivity.EXTRA_NAME, "Azzar Rizz")
                Intent.putExtra(MoveWithDataActivity.EXTRA_AGE,20)
                startActivity(Intent)
            }

        }
    }


    }



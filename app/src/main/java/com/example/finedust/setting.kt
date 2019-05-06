package com.example.finedust

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_setting.*

class setting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        var button = findViewById(R.id.button6) as Button
        button.setOnClickListener {
            val login_page_move = Intent(this, login_page::class.java)
            startActivity(login_page_move)
        }
    }
}

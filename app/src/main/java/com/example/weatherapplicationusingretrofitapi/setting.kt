package com.example.weatherapplicationusingretrofitapi

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class setting : AppCompatActivity() {

    private lateinit var spinner:Spinner
    private lateinit var adapter : ArrayAdapter<CharSequence>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.Toolbar)
        toolbar.title = "Setting"
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        spinner = findViewById(R.id.spinner)
        adapter = ArrayAdapter.createFromResource(this, R.array.lang, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner.adapter = adapter

    }


}
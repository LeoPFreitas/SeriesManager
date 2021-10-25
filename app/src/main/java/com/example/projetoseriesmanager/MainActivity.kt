package com.example.projetoseriesmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projetoseriesmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
//    private val

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
    }
}
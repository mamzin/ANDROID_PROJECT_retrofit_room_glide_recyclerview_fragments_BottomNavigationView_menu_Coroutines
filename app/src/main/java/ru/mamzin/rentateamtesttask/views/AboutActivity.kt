package ru.mamzin.rentateamtesttask.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.mamzin.rentateamtesttask.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish();
    }
}
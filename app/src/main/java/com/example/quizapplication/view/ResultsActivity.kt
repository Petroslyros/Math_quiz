package com.example.quizapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.quizapplication.R
import com.example.quizapplication.databinding.ActivityResultsBinding

class ResultsActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_results)

        binding.txtAnswer.text = "Your score is :" +MainActivity.result + "/"+MainActivity.totalQuestions

        binding.backBtn.setOnClickListener() {
            var intent = Intent(this@ResultsActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
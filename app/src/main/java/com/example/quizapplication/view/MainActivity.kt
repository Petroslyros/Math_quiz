package com.example.quizapplication.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapplication.R
import com.example.quizapplication.databinding.ActivityMainBinding
import com.example.quizapplication.model_classes.Question
import com.example.quizapplication.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var questionsList: List<Question>

    companion object {
        var result = 0
        var totalQuestions = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Resetting the scores
        result = 0
        totalQuestions = 0

        // Initializing ViewModel
        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        // Observing data from ViewModel
        GlobalScope.launch(Dispatchers.Main) {
            quizViewModel.getQuestionsFromLiveData()
                .observe(this@MainActivity, Observer { questions ->
                    if (questions.isNotEmpty()) {
                        questionsList = questions
                        totalQuestions = questionsList.size

                        // Display the first question
                        displayQuestion(0)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "No questions available",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }

        var currentIndex = 0

        binding.buttonNext.setOnClickListener {
            val selectedOptionId = binding.radioGroup.checkedRadioButtonId

            // Check if an option is selected
            if (selectedOptionId == -1) {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedRadioButton = findViewById<RadioButton>(selectedOptionId)

            // Check if the selected answer is correct
            if (selectedRadioButton.text.toString() == questionsList[currentIndex].correct_option) {
                result++
                binding.txtResult.text = "Correct Answers: $result"
            }

            currentIndex++

            // If this was the last question, navigate to ResultsActivity
            if (currentIndex == totalQuestions) {
                val intent = Intent(this, ResultsActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Display the next question
                displayQuestion(currentIndex)

                // Update button text to "FINISH" if it's the last question
                if (currentIndex == totalQuestions - 1) {
                    binding.buttonNext.text = "FINISH"
                }
            }

            // Clear the selected option
            binding.radioGroup.clearCheck()
        }
    }

    private fun displayQuestion(index: Int) {
        val question = questionsList[index]
        binding.apply {
            txtQuestion.text = "Question ${index + 1}: ${question.question}"
            radio1.text = question.option1
            radio2.text = question.option2
            radio3.text = question.option3
            radio4.text = question.option4
        }
    }
}


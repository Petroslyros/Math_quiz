package com.example.quizapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quizapplication.model_classes.QuestionsList
import com.example.quizapplication.repository.QuizRepository

class QuizViewModel : ViewModel() {

    //connect with the repository

    private var repository : QuizRepository = QuizRepository()

    var questionsLiveData : LiveData<QuestionsList>

    init {
        questionsLiveData = repository.getQuestionsFromAPI()
    }

    fun getQuestionsFromLiveData(): LiveData<QuestionsList> {
        return questionsLiveData
    }
}
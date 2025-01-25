package com.example.quizapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizapplication.model_classes.QuestionsList
import com.example.quizapplication.retrofit.QuestionsAPI
import com.example.quizapplication.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizRepository {

    var questionsAPI : QuestionsAPI

    init {
        questionsAPI = RetrofitInstance().getRetrofitInstance().create(QuestionsAPI::class.java)

    }

    fun getQuestionsFromAPI() : LiveData<QuestionsList>{

        //Live data
        val data = MutableLiveData<QuestionsList>()
        var questionsList : QuestionsList

        GlobalScope.launch(Dispatchers.IO) {
            //Return the Response<QuestionsList>
            val response = questionsAPI.getQuestions()

            questionsList = response.body()!!
            data.postValue(questionsList)

        }
        return data
    }
}
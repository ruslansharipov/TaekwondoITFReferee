package com.sharipov.taekwondoitfreferee.activity_main

import androidx.lifecycle.ViewModel
import com.sharipov.taekwondoitfreferee.repository.QuestionRepository

class MainViewModel: ViewModel() {
    val questions = QuestionRepository.questions
    val papersLiveData = QuestionRepository.papers
    val themes = QuestionRepository.themes
}
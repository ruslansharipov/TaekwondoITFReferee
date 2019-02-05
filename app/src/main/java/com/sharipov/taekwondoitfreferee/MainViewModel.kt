package com.sharipov.taekwondoitfreferee

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val questions = QuestionRepository.questions
    val papersLiveData = QuestionRepository.papers
    val themes = QuestionRepository.themes
}
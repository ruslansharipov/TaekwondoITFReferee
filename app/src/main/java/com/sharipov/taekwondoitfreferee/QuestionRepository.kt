package com.sharipov.taekwondoitfreferee

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

const val QUESTIONS = "questions"
const val SORT_BY = "sortBy"
const val PAPER = "paper"
const val THEME = "theme"

suspend fun <DocumentSnapshot> awaitCallback(block: (EventListener<DocumentSnapshot>) -> Unit): DocumentSnapshot =
    suspendCancellableCoroutine { cont ->
        block(object : EventListener<DocumentSnapshot> {
            override fun onEvent(p0: DocumentSnapshot?, p1: FirebaseFirestoreException?) {
                if (p1 != null) p1.let { cont.resumeWithException(it) }
                else p0?.let { cont.resume(p0) }
            }
        })
    }


object QuestionRepository {
    private lateinit var questionsList: List<Question>

    private val _questions: MutableLiveData<List<Question>> = MutableLiveData()
    val questions: LiveData<List<Question>>
        get() = getQuestionsLiveData()

    private val _themes: MutableLiveData<List<Theme>> = MutableLiveData()
    val themes: LiveData<List<Theme>>
        get() = getThemesLiveData()

    private val _papers: MutableLiveData<List<Int>> = MutableLiveData()
    val papers: LiveData<List<Int>>
        get() = getPapersLiveData()

    fun sortedByPaper(paper: Int): List<Question> = questionsList.filter { it.paperNumber?.equals(paper) ?: false }

    fun sortedByTheme(theme: String): List<Question> = questionsList.filter { it.theme?.equals(theme) ?: false }

    private fun countByTheme(theme: String): Int = questionsList.count { it.theme == theme }

    private fun getPapersLiveData(): LiveData<List<Int>> {
        if (_papers.value == null) {
            FirebaseFirestore.getInstance()
                .collection(SORT_BY)
                .document(PAPER)
                .addSnapshotListener { snapshot, exception ->
                    exception?.let { onError(it) }
                    snapshot?.let {
                        _papers.postValue(
                            it.toObject(Papers::class.java)
                                ?.papers
                        )
                    }
                }
        }
        return _papers
    }

    private fun getThemesLiveData(): LiveData<List<Theme>> {
        if (_themes.value == null) {
            FirebaseFirestore.getInstance()
                .collection(SORT_BY)
                .document(THEME)
                .addSnapshotListener { snapshot, exception ->
                    exception?.let { onError(it) }
                    snapshot?.let {
                        val list = ArrayList<Theme>()
                        it.toObject(Themes::class.java)
                            ?.themes
                            ?.forEach { s -> list.add(Theme(s, countByTheme(s))) }
                        _themes.postValue(list)
                    }
                }
        }
        return _themes
    }

    private fun getQuestionsLiveData(): LiveData<List<Question>> {
        if (_questions.value == null) {
            FirebaseFirestore.getInstance()
                .collection(QUESTIONS)
                .addSnapshotListener { snapshot, exception ->
                    exception?.let { onError(it) }
                    snapshot?.let {
                        questionsList = it.toObjects(Question::class.java)
                        _questions.postValue(questionsList)
                        Log.d("itf", questionsList.size.toString())
                    }
                }
        }
        return _questions
    }

    private fun onError(e: Exception) = Log.d("error", e.message, e)
}

class Papers {
    var papers: List<Int>? = null

    constructor()
}

class Themes {
    var themes: List<String>? = null

    constructor()
}

class Question {
    var question: String? = null
    var hint: String? = null
    var answer: String? = null
    var answers: List<String>? = null
    var pictureUrl: String? = null
    var paperNumber: Int? = null
    var theme: String? = null

    constructor()

//    constructor(
//        question: String?,
//        hint: String?,
//        answer: String?,
//        answers: List<String>?,
//        pictureUrl: String?,
//        paperNumber: Int?,
//        theme: String?
//    ) {
//        this.question = question
//        this.hint = hint
//        this.answer = answer
//        this.answers = answers
//        this.pictureUrl = pictureUrl
//        this.paperNumber = paperNumber
//        this.theme = theme
//    }
}

data class Theme(val theme: String, val count: Int)
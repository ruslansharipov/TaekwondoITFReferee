package com.sharipov.taekwondoitfreferee.fragment.questions


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.sharipov.taekwondoitfreferee.activity_main.MainViewModel
import com.sharipov.taekwondoitfreferee.repository.Question
import com.sharipov.taekwondoitfreferee.R
import com.sharipov.taekwondoitfreferee.fragment.questions.QuestionsArgs.filter
import kotlinx.android.synthetic.main.fragment_questions_pager.*
import kotlinx.android.synthetic.main.fragment_questions_pager.view.*

class QuestionsPagerFragment : Fragment(), QuestionsPagerAdapter.ScrollController {
    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_questions_pager, container, false)
        val pager = view.findViewById<ViewPager>(R.id.viewPager)
        view.tabLayout.setupWithViewPager(pager)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.questions.observe(this, Observer {
            val questionsAdapter = QuestionsPagerAdapter(fragmentManager!!).apply {
                questionList = filterQuestions(it)
                mainScrollController = this@QuestionsPagerFragment
            }
            pager.adapter = questionsAdapter
        })
        return view
    }

    override fun scrollToNextPage() {
        viewPager.currentItem += 1
    }

    private fun filterQuestions(list: List<Question>): List<Question> = when (filter) {
        is String -> list.filter { it.theme == filter }
        is Int -> list.filter { it.paperNumber == filter }
        else -> emptyList()
    }
}
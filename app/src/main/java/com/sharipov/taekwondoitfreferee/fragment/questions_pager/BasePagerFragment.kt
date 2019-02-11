package com.sharipov.taekwondoitfreferee.fragment.questions_pager


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.sharipov.taekwondoitfreferee.R
import com.sharipov.taekwondoitfreferee.activity_main.MainViewModel
import com.sharipov.taekwondoitfreferee.repository.Question
import kotlinx.android.synthetic.main.fragment_questions_pager.*
import kotlinx.android.synthetic.main.fragment_questions_pager.view.*

abstract class BasePagerFragment : Fragment(), QuestionsPagerAdapter.ScrollController {
    private lateinit var questionsAdapter: QuestionsPagerAdapter
    private var currentPage: Int = 0

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_questions_pager, container, false)
        view.tabLayout.setupWithViewPager(viewPager)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.questions.observe(this, Observer {
            questionsAdapter = QuestionsPagerAdapter(fragmentManager!!).apply {
                questionList = filterQuestions(it)
                mainScrollController = this@BasePagerFragment
            }
            viewPager.adapter = questionsAdapter
            viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    currentPage = position
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    questionsAdapter.scrollListeners[position]?.collapseBottomSheet()
                }
            })
        })
        return view
    }

    override fun onDestroy() {
        questionsAdapter.backPressedListeners[currentPage]?.cancelTheJob()
        super.onDestroy()
    }

    override fun scrollToNextPage() {
        viewPager.currentItem += 1
    }

    abstract fun filterQuestions(list: List<Question>): List<Question>
}
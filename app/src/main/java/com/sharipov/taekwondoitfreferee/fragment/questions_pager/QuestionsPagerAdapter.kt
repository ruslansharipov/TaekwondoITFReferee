package com.sharipov.taekwondoitfreferee.fragment.questions_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sharipov.taekwondoitfreferee.fragment.question.OnPageScrolledListener
import com.sharipov.taekwondoitfreferee.fragment.question.QuestionFragment
import com.sharipov.taekwondoitfreferee.repository.Question

class QuestionsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    lateinit var questionList: List<Question>
    lateinit var mainScrollController: ScrollController

    val scrollListeners = HashMap<Int, OnPageScrolledListener>()

    override fun getItem(position: Int): Fragment = QuestionFragment().apply {
        question = questionList[position]
        scrollController = mainScrollController
        scrollListeners[position] = this as OnPageScrolledListener
    }

    override fun getCount(): Int = questionList.size

    override fun getPageTitle(position: Int): CharSequence? = (position + 1).toString()

    interface ScrollController {
        fun scrollToNextPage()
    }
}
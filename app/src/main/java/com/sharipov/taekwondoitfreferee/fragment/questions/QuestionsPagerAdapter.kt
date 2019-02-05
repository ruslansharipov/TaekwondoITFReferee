package com.sharipov.taekwondoitfreferee.fragment.questions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sharipov.taekwondoitfreferee.repository.Question

class QuestionsPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    lateinit var questionList: List<Question>
    lateinit var mainScrollController: ScrollController

    override fun getItem(position: Int): Fragment = QuestionFragment().apply {
        question = questionList[position]
        scrollController = mainScrollController
    }

    override fun getCount(): Int = questionList.size

    override fun getPageTitle(position: Int): CharSequence? = (position + 1).toString()

    interface ScrollController {
        fun scrollToNextPage()
    }
}
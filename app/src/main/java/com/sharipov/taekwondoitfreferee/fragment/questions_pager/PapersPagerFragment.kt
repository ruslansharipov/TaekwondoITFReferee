package com.sharipov.taekwondoitfreferee.fragment.questions_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.sharipov.taekwondoitfreferee.repository.Question
import kotlinx.android.synthetic.main.activity_main.*

class PapersPagerFragment : BasePagerFragment() {
    private val args: PapersPagerFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.toolbar?.subtitle = "Paper #${args.paper}"
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun filterQuestions(list: List<Question>): List<Question> =
        list.filter { it.paperNumber == args.paper }
}
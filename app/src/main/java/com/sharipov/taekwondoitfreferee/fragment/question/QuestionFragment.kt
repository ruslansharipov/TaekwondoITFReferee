package com.sharipov.taekwondoitfreferee.fragment.question


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sharipov.taekwondoitfreferee.R
import com.sharipov.taekwondoitfreferee.fragment.questions_pager.QuestionsPagerAdapter
import com.sharipov.taekwondoitfreferee.repository.Question
import kotlinx.android.synthetic.main.fragment_question.view.*
import kotlinx.android.synthetic.main.question_bottom_sheet.*
import kotlinx.android.synthetic.main.question_bottom_sheet.view.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class QuestionFragment : Fragment(), OnPageScrolledListener, CoroutineScope, JobCanceller {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    lateinit var question: Question
    lateinit var scrollController: QuestionsPagerAdapter.ScrollController
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<CardView>
    private lateinit var buttons: Array<Button>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
            .apply {
                buttons = arrayOf(button1, button2, button3, button4)
                bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

                questionTextView.text = question.question
                Glide.with(context)
                    .load(question.pictureUrl)
                    .into(questionImageView)
                buttons.forEachIndexed { i, button -> button.text = question.answers?.get(i) }
                setListeners()

                answerTextView.text = String.format(context.getString(R.string.correct_answer), question.answer)
                Glide.with(context)
                    .load(question.pictureUrl)
                    .into(hintImageView)
                hintTextView.append("\n${question.hint}")
                hintButton.setOnClickListener { bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED }

                bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(p0: View, p1: Float) {}
                    override fun onStateChanged(p0: View, state: Int) = when (state) {
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> onStateHalfExpanded()
                        BottomSheetBehavior.STATE_EXPANDED -> onStateExpanded()
                        BottomSheetBehavior.STATE_COLLAPSED -> onStateCollapsed()
                        else -> { }
                    }
                })
            }
    }

    private fun setListeners() =
        buttons.forEach { b -> b.setOnClickListener { checkAnswer(b) } }

    private fun removeListeners() =
        buttons.forEach { it.setOnClickListener(null) }


    private fun onStateExpanded() {
        removeListeners()
        hintButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down, 0, 0, 0)
        hintButton.setOnClickListener { bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED }
    }

    private fun onStateCollapsed() {
        hintButton.setOnClickListener { bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED }
        hintButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_up, 0, 0, 0)
        setListeners()
    }

    private fun onStateHalfExpanded() {
        removeListeners()
        hintButton.setOnClickListener { bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED }
    }

    private fun checkAnswer(b: Button) = launch {
        if (b.text == question.answer) {
            b.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.answer_correct, 0)
            delay(DELAY_TIME_MS)
            b.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            scrollController.scrollToNextPage()
        } else {
            b.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.answer_wrong, 0)
            delay(DELAY_TIME_MS)
            b.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun collapseBottomSheet() {
        if (::bottomSheetBehavior.isInitialized) bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDestroy() {
        cancelTheJob()
        super.onDestroy()
    }

    override fun cancelTheJob() = job.cancel()

    companion object {
        private const val DELAY_TIME_MS = 400L
    }
}

interface OnPageScrolledListener {
    fun collapseBottomSheet()
}

interface JobCanceller {
    fun cancelTheJob()
}
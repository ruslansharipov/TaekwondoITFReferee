package com.sharipov.taekwondoitfreferee.fragment.question


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sharipov.taekwondoitfreferee.R
import com.sharipov.taekwondoitfreferee.activity_main.OnBackPressedListener
import com.sharipov.taekwondoitfreferee.fragment.questions_pager.QuestionsPagerAdapter
import com.sharipov.taekwondoitfreferee.repository.Question
import kotlinx.android.synthetic.main.fragment_question.view.*
import kotlinx.android.synthetic.main.question_bottom_sheet.view.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class QuestionFragment : Fragment(), OnPageScrolledListener, CoroutineScope, OnBackPressedListener {
    private val questionJob = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + questionJob

    lateinit var question: Question
    lateinit var scrollController: QuestionsPagerAdapter.ScrollController
    lateinit var bottomSheetBehavior: BottomSheetBehavior<CardView>
    lateinit var buttons: Array<Button>

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
                buttons.forEachIndexed { i, button ->
                    button.text = question.answers?.get(i)
                    button.setOnClickListener { checkAnswer(it as Button) }
                }

                answerTextView.text = String.format(context.getString(R.string.correct_answer), question.answer)
                Glide.with(context)
                    .load(question.pictureUrl)
                    .into(hintImageView)
                hintTextView.append("\n${question.hint}")
                hintButton.setOnClickListener { showBottomSheet() }

                bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(p0: View, p1: Float) {}
                    override fun onStateChanged(p0: View, state: Int) {
                        if (state == BottomSheetBehavior.STATE_EXPANDED) clearButtonsDrawables()
                    }
                })
            }
    }

    private fun clearButtonsDrawables() = buttons.forEach {
        it.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    private fun showBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun checkAnswer(b: Button) = launch(coroutineContext) {
        if (b.text == question.answer) {
            b.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.answer_correct, 0)
            delay(DELAY_TIME_MS)
            b.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            scrollController.scrollToNextPage()
        } else {
            b.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.answer_wrong, 0)
            delay(DELAY_TIME_MS)
            showBottomSheet()
        }
    }

    override fun onPageScrolled() {
        if (::bottomSheetBehavior.isInitialized) bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("itf", "onDestroy(): questionJob.isCancelled = ${questionJob.isCancelled}")
        questionJob.cancel()
        Log.d("itf", "onDestroy(): questionJob.isCancelled = ${questionJob.isCancelled}")
    }

    override fun onBackPressed() {
        Log.d("itf", "onBackPressed(): questionJob.isCancelled = ${questionJob.isCancelled}")
        questionJob.cancel()
        Log.d("itf", "onBackPressed(): questionJob.isCancelled = ${questionJob.isCancelled}")
    }

    companion object {
        private const val DELAY_TIME_MS = 400L
    }
}

interface OnPageScrolledListener {
    fun onPageScrolled()
}
package com.sharipov.taekwondoitfreferee.fragment.questions


import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sharipov.taekwondoitfreferee.repository.Question
import com.sharipov.taekwondoitfreferee.R
import kotlinx.android.synthetic.main.fragment_question.view.*

class QuestionFragment : Fragment(), View.OnClickListener {
    lateinit var question: Question
    lateinit var scrollController: QuestionsPagerAdapter.ScrollController

    lateinit var buttons: Array<Button>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
            .apply {
                buttons = arrayOf(button1, button2, button3, button4)

                questionTextView.text = question.question
                Glide.with(context)
                    .load(question.pictureUrl)
                    .into(questionImageView)

                buttons.forEachIndexed { i, button ->
                    button.text = question.answers?.get(i)
                    button.setOnClickListener(this@QuestionFragment)
                }
            }
    }

    private fun scrollToTheNextQuestion() {
        scrollController.scrollToNextPage()
    }

    override fun onClick(v: View?) {
        if (v is Button) {
            val correctColor = ContextCompat.getColor(v.context, R.color.answerCorrect)
            val incorrectColor = ContextCompat.getColor(v.context, R.color.answerIncorrect)
            if (v.text == question.answer) {
                v.setCompoundDrawablesWithIntrinsicBounds(R.drawable.answer_correct, 0, 0, 0)
                v.setTextColor(correctColor)
            } else {
                val sb = SpannableStringBuilder()
                sb.append("${v.text}\n", ForegroundColorSpan(incorrectColor), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                sb.append("Hint: ${question.hint}\n")
                sb.append(
                    "Correct answer: ${question.answer}",
                    ForegroundColorSpan(correctColor),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                v.text = sb
                v.setCompoundDrawablesWithIntrinsicBounds(R.drawable.answer_wrong, 0, 0, 0)
            }
        }
        buttons.forEach { it.isEnabled = false }
    }


    private fun Fragment.toast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}
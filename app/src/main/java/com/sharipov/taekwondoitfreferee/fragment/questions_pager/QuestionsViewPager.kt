package com.sharipov.taekwondoitfreferee.fragment.questions_pager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class QuestionsViewPager(context: Context, attrs: AttributeSet?): ViewPager(context, attrs) {
    private var isPagingEnabled: Boolean = false

    constructor(context: Context) : this(context, null)

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return isPagingEnabled && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return isPagingEnabled && super.onInterceptTouchEvent(ev)
    }
}
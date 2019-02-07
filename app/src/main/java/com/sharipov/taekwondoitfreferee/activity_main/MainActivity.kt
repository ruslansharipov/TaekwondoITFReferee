package com.sharipov.taekwondoitfreferee.activity_main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sharipov.taekwondoitfreferee.R
import com.sharipov.taekwondoitfreferee.fragment.question.QuestionsArgs.filter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val currentFragment: Fragment?
        get() = nav_host_fragment.childFragmentManager.fragments[0]

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)
        bottomNav.setupWithNavController(navController)
        toolbar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.questionsPagerFragment -> {
                    bottomNav.visibility = View.GONE
                    setupSubtitle(filter)
                }
                else -> {
                    bottomNav.visibility = View.VISIBLE
                    setupSubtitle(null)
                }
            }
        }
    }

    override fun onBackPressed() {
        Log.d("itf", "onBackPressed()")
        val f = currentFragment
        if (f is OnBackPressedListener) {
            f.onBackPressed()
            Log.d("itf", "currentFragment.onBackPressed()")
        }
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d("itf", "onSupportNavigateUp()")
        return navController.navigateUp()
    }

    private fun setupSubtitle(subtitle: Any?) = when (subtitle) {
        is String -> toolbar.subtitle = subtitle
        is Int -> toolbar.subtitle = "Paper #$subtitle"
        else -> toolbar.subtitle = null
    }
}

interface OnBackPressedListener {
    fun onBackPressed()
}

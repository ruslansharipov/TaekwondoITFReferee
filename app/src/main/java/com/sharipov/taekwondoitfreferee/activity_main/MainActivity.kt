package com.sharipov.taekwondoitfreferee.activity_main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sharipov.taekwondoitfreferee.R
import com.sharipov.taekwondoitfreferee.fragment.question.QuestionsArgs.filter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun setupSubtitle(subtitle: Any?) = when (subtitle) {
        is String -> toolbar.subtitle = subtitle
        is Int -> toolbar.subtitle = "Paper #$subtitle"
        else -> toolbar.subtitle = null
    }
}

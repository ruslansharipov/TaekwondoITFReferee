package com.sharipov.taekwondoitfreferee.activity_main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sharipov.taekwondoitfreferee.R
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
                R.id.papersPagerFragment, R.id.themesPagerFragment -> {
                    bottomNav.visibility = View.GONE
                }
                else -> {
                    bottomNav.visibility = View.VISIBLE
                    toolbar.subtitle = null
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}

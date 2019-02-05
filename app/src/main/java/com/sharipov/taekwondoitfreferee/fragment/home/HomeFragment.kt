package com.sharipov.taekwondoitfreferee.fragment.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sharipov.taekwondoitfreferee.R
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navController = findNavController()
        return inflater.inflate(R.layout.fragment_home, container, false)
            .apply {
                papersButton.setOnClickListener { navController.navigate(R.id.action_homeFragment_to_papersFragment) }
                themesButton.setOnClickListener { navController.navigate(R.id.action_homeFragment_to_themesFragment) }
            }
    }

}

package com.sharipov.taekwondoitfreferee.themes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.taekwondoitfreferee.R
import com.sharipov.taekwondoitfreferee.Theme
import com.sharipov.taekwondoitfreferee.questions.QuestionsArgs
import kotlinx.android.synthetic.main.themes_list_item.view.*

class ThemesAdapter(private val themes: List<Theme>) : RecyclerView.Adapter<ThemesAdapter.ThemesViewHolder>() {
    private var filteredThemes: List<Theme> = themes

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.themes_list_item, parent, false)
        return ThemesViewHolder(view)
    }

    override fun getItemCount(): Int = filteredThemes.size

    override fun onBindViewHolder(holder: ThemesViewHolder, position: Int) = with(holder.itemView) {
        val theme = filteredThemes[position]
        titleTextView.text = theme.theme
        countTextView.text = String.format(context.getString(R.string.questions_count), theme.count)
        setOnClickListener {
            QuestionsArgs.filter = theme.theme
            findNavController().navigate(R.id.action_themesFragment_to_questionsPagerFragment)
        }
    }

    fun filterThemes(query: String?): Boolean {
        filteredThemes = themes.filter { it.theme.contains(other = query as CharSequence, ignoreCase = true) }
        notifyDataSetChanged()
        return true
    }

    inner class ThemesViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
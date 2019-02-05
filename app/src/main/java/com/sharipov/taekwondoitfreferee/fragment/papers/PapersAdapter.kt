package com.sharipov.taekwondoitfreferee.fragment.papers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.taekwondoitfreferee.R
import com.sharipov.taekwondoitfreferee.fragment.question.QuestionsArgs
import kotlinx.android.synthetic.main.papers_grid_item.view.*

class PapersAdapter(private val papers: List<Int>): RecyclerView.Adapter<PapersAdapter.PapersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PapersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.papers_grid_item, parent, false)
        return PapersViewHolder(view)
    }

    override fun getItemCount(): Int = papers.size

    override fun onBindViewHolder(holder: PapersViewHolder, position: Int) = with(holder.itemView){
        val paperNumber = papers[position]
        paperNumberTextView.text = paperNumber.toString()
        setOnClickListener {
            QuestionsArgs.filter = paperNumber
            findNavController().navigate(R.id.action_papersFragment_to_questionsPagerFragment)
        }
    }

    inner class PapersViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

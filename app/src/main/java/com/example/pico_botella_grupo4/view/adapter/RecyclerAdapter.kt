package com.example.pico_botella_grupo4.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pico_botella_grupo4.databinding.ItemChallengeBinding
import com.example.pico_botella_grupo4.model.Challenge
import com.example.pico_botella_grupo4.view.viewholder.RecyclerViewHolder

class RecyclerAdapter(
    private var challengeList: List<Challenge>,
    private val onEdit: (Challenge) -> Unit,
    private val onDelete: (Challenge) -> Unit
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {

        val binding = ItemChallengeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return challengeList.size
    }

    override fun onBindViewHolder(
        holder: RecyclerViewHolder,
        position: Int
    ) {

        val challenge = challengeList[position]

        holder.setItemChallenge(
            challenge,
            onEdit,
            onDelete
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateChallenges(newChallenges: List<Challenge>) {

        challengeList = newChallenges

        notifyDataSetChanged()
    }
}
package com.example.pico_botella_grupo4.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.pico_botella_grupo4.databinding.ItemChallengeBinding
import com.example.pico_botella_grupo4.model.Challenge

class RecyclerViewHolder(
    binding: ItemChallengeBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val bindingItem = binding

    fun setItemChallenge(
        challenge: Challenge,
        onEdit: (Challenge) -> Unit,
        onDelete: (Challenge) -> Unit
    ) {

        bindingItem.tvDescription.text = challenge.description

        bindingItem.btnEdit.setOnClickListener {
            onEdit(challenge)
        }

        bindingItem.btnDelete.setOnClickListener {
            onDelete(challenge)
        }
    }
}
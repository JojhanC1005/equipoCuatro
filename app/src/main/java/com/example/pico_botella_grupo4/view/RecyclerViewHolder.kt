package com.example.pico_botella_grupo4.view

import androidx.recyclerview.widget.RecyclerView
import com.example.pico_botella_grupo4.databinding.ItemChallengeBinding

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
package com.example.pico_botella_grupo4.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pico_botella_grupo4.R
import android.widget.ImageView
import com.bumptech.glide.Glide

class InstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_instructions,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val title = view.findViewById<TextView>(R.id.tvToolbarTitle)
        title.text = getString(R.string.instruccionTitulo)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val imgVictory = view.findViewById<ImageView>(R.id.imgVictory)

        Glide.with(this)
            .asGif()
            .load(R.raw.sans_victory)
            .into(imgVictory)

    }

}
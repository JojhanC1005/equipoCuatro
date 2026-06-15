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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AlertDialog

class ChallengesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_challenges,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Changing title
        val title = view.findViewById<TextView>(R.id.tvToolbarTitle)
        title.text = getString(R.string.challenges_title)

        // Configuring back button
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        //Add challenge button
        val fab = view.findViewById<FloatingActionButton>(R.id.fabAddChallenge)

        fab.setOnClickListener {

            AlertDialog.Builder(requireContext())
                .setTitle("placeholder")
                .setMessage("")
                .setPositiveButton("Aceptar", null)
                .show()
        }

    }
}
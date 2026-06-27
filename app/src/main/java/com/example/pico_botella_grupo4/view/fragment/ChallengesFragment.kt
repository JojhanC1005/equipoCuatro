package com.example.pico_botella_grupo4.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pico_botella_grupo4.R
import com.example.pico_botella_grupo4.databinding.FragmentChallengesBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.pico_botella_grupo4.view.Challenge
import com.example.pico_botella_grupo4.view.RecyclerAdapter
import android.widget.EditText

class ChallengesFragment : Fragment() {

    private var _binding: FragmentChallengesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChallengesBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
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

        // Add challenge button
        val fab = view.findViewById<FloatingActionButton>(R.id.fabAddChallenge)

        fab.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("placeholder")
                .setMessage("")
                .setPositiveButton("Aceptar", null)
                .show()
        }

        recycler()
    }

    private fun recycler() {

        val listaChallenge = mutableListOf(
            Challenge("Hee hee hee."),
            Challenge("Did you REALLY think killing me would make a DIFFERENCE?"),
            Challenge("No."),
            Challenge("Every time you load your SAVE, I'll come back."),
            Challenge("And every time you try to get a happy ending..."),
            Challenge("I'll be there to tear it away!")
        )

        binding.recyclerview.layoutManager =
            LinearLayoutManager(requireContext())


        val adapter = RecyclerAdapter(

            listaChallenge,

            onEdit = { challenge ->

                val editText = EditText(requireContext())

                editText.setText(challenge.description)

                AlertDialog.Builder(requireContext())
                    .setTitle("Editar reto")
                    .setView(editText)

                    .setPositiveButton("Guardar") { _, _ ->

                        challenge.description =
                            editText.text.toString()

                        binding.recyclerview.adapter?.notifyDataSetChanged()
                    }

                    .setNegativeButton("Cancelar", null)
                    .show()
            },

            onDelete = { challenge ->

                AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar reto")
                    .setMessage("¿Deseas eliminar este reto?")
                    .setPositiveButton("Sí", null)
                    .setNegativeButton("No", null)
                    .show()
            }
        )

        binding.recyclerview.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
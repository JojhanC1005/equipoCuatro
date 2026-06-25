package com.example.pico_botella_grupo4.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.compose.material3.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pico_botella_grupo4.R
import com.example.pico_botella_grupo4.databinding.FragmentChallengesBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.pico_botella_grupo4.view.Challenge
import com.example.pico_botella_grupo4.view.RecyclerAdapter

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

//        fab.setOnClickListener {
//            AlertDialog.Builder(requireContext())
//                .setTitle("placeholder")
//                .setMessage("")
//                .setPositiveButton("Aceptar", null)
//                .show()
//        }
        fab.setOnClickListener {

            val dialogView = layoutInflater.inflate(
                R.layout.dialog_add_challenge,
                null
            )

            val editText =
                dialogView.findViewById<EditText>(
                    R.id.etChallenge
                )

            val btnCancel =
                dialogView.findViewById<Button>(
                    R.id.btnCancel
                )

            val btnSave =
                dialogView.findViewById<Button>(
                    R.id.btnSave
                )

            btnSave.isEnabled = false
            btnSave.alpha = 0.5f

            editText.addTextChangedListener {

                val hasText =
                    !it.isNullOrBlank()

                btnSave.isEnabled = hasText

                btnSave.alpha =
                    if (hasText) 1f else 0.5f
            }

            val dialog = AlertDialog.Builder(
                requireContext()
            )
                .setView(dialogView)
                .create()

            dialog.setCanceledOnTouchOutside(false)

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            btnSave.setOnClickListener {

                val description =
                    editText.text.toString().trim()

                if (description.isNotEmpty()) {

                    // Aquí irá el insert real

                    dialog.dismiss()
                }
            }

            dialog.show()

            dialog.window?.setBackgroundDrawableResource(
                android.R.color.transparent
            )
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

                val dialogView = layoutInflater.inflate(
                    R.layout.dialog_edit_challenge,
                    null
                )

                val editText =
                    dialogView.findViewById<EditText>(
                        R.id.etChallenge
                    )

                val btnCancel =
                    dialogView.findViewById<Button>(
                        R.id.btnCancel
                    )

                val btnSave =
                    dialogView.findViewById<Button>(
                        R.id.btnSave
                    )

                editText.setText(
                    challenge.description
                )

                val dialog = AlertDialog.Builder(
                    requireContext()
                )
                    .setView(dialogView)
                    .create()

                dialog.setCanceledOnTouchOutside(false)

                btnCancel.setOnClickListener {
                    dialog.dismiss()
                }

                btnSave.setOnClickListener {

                    val newDescription =
                        editText.text.toString()

                    // Aquí irá el update en Room

                    dialog.dismiss()
                }

                dialog.show()
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
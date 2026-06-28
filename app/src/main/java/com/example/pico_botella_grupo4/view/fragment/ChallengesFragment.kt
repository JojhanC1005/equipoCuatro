package com.example.pico_botella_grupo4.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pico_botella_grupo4.R
import com.example.pico_botella_grupo4.data.DatabaseProvider
import com.example.pico_botella_grupo4.databinding.DialogAddChallengeBinding
import com.example.pico_botella_grupo4.databinding.DialogDeleteChallengeBinding
import com.example.pico_botella_grupo4.databinding.DialogEditChallengeBinding
import com.example.pico_botella_grupo4.databinding.FragmentChallengesBinding
import com.example.pico_botella_grupo4.model.Challenge
import com.example.pico_botella_grupo4.repository.ChallengeRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.pico_botella_grupo4.view.adapter.RecyclerAdapter
import com.example.pico_botella_grupo4.viewmodel.ChallengeViewModel
import com.example.pico_botella_grupo4.viewmodel.ChallengeViewModelFactory

class ChallengesFragment : Fragment() {

    private var _binding: FragmentChallengesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ChallengeViewModel
    private lateinit var adapter: RecyclerAdapter

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

        setUpViewModel()
        setupToolbar()
        setupFab()
        setUpRecycler()
        observeChallenges()
    }

    private fun setUpRecycler() {

        adapter = RecyclerAdapter(

            emptyList(),

            onEdit = { challenge ->
                showEditChallengeDialog(challenge)
            },

            onDelete = { challenge ->
                showDeleteDialog(challenge)
            }
        )

        binding.recyclerview.layoutManager =
            LinearLayoutManager(requireContext())

        binding.recyclerview.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpViewModel() {
        // Obtener el DAO de Room
        val dao = DatabaseProvider
            .getDatabase(requireContext())
            .challengeDao()

        // Comunicar el repository con Room
        val repository = ChallengeRepository(dao)

        val factory = ChallengeViewModelFactory(repository)

        // Crear instancia del ChallengeViewModel
        viewModel = ViewModelProvider(
            this,
            factory
        )[ChallengeViewModel::class.java]
    }

    private fun setupToolbar() {

        binding.contentToolbarChallenges.tvToolbarTitle.text =
            getString(R.string.challenges_title)

        binding.contentToolbarChallenges.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupFab() {

        binding.fabAddChallenge.setOnClickListener {
            showAddChallengeDialog()
        }
    }

    private fun showAddChallengeDialog() {

        val dialogBinding = DialogAddChallengeBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialog.setCanceledOnTouchOutside(false)

        // Desactivar botón guardar por defecto
        dialogBinding.btnSave.isEnabled = false
        dialogBinding.btnSave.alpha = 0.5f

        dialogBinding.etChallenge.addTextChangedListener {

            val hasText = !it.isNullOrBlank()

            dialogBinding.btnSave.isEnabled = hasText
            dialogBinding.btnSave.alpha = if (hasText) 1f else 0.5f
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnSave.setOnClickListener {

            val description =
                dialogBinding.etChallenge.text.toString().trim()

            if (description.isNotEmpty()) {
                viewModel.insert(description)
                dialog.dismiss()
            }
        }

        dialog.show()

        dialog.window?.setBackgroundDrawableResource(
            android.R.color.transparent
        )
    }

    private fun showEditChallengeDialog(challenge: Challenge) {
        val dialogBinding =
            DialogEditChallengeBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialog.setCanceledOnTouchOutside(false)

        dialogBinding.etChallenge.setText(
            challenge.description
        )

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnSave.setOnClickListener {

            val newDescription =
                dialogBinding.etChallenge.text.toString().trim()

            if (newDescription.isNotEmpty()) {

                viewModel.update(
                    challenge.copy(
                        description = newDescription
                    )
                )

                dialog.dismiss()
            }
        }

        dialog.show()

        dialog.window?.setBackgroundDrawableResource(
            android.R.color.transparent
        )
    }

    private fun showDeleteDialog(
        challenge: Challenge
    ) {

        val dialogBinding =
            DialogDeleteChallengeBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialog.setCanceledOnTouchOutside(false)

        dialogBinding.tvChallengeDescription.text = challenge.description

        dialogBinding.tvNo.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.tvYes.setOnClickListener {

            viewModel.delete(challenge)

            dialog.dismiss()
        }

        dialog.show()

        dialog.window?.setBackgroundDrawableResource(
            android.R.color.transparent
        )
    }

    private fun observeChallenges() {

        viewModel.challenges.observe(
            viewLifecycleOwner
        ) {
            adapter.updateChallenges(it)
        }
    }
}
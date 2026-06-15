package com.example.pico_botella_grupo4.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pico_botella_grupo4.R
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.content.Intent
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import android.media.MediaPlayer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var mediaPlayer: MediaPlayer? = null
    private var audioActivo = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mediaPlayer = MediaPlayer.create(
            requireContext(),
            R.raw.musica_fondo
        )

        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        val btnGirar = view.findViewById<ImageButton>(R.id.btn_girar_botella)
        val btnCalificar = view.findViewById<ImageButton>(R.id.btn_calificar)
        val btnVolumen = view.findViewById<ImageButton>(R.id.btn_volumen)
        val btnInstrucciones = view.findViewById<ImageButton>(R.id.btn_instrucciones)
        val btnRetos = view.findViewById<ImageButton>(R.id.btn_retos)
        val btnCompartir = view.findViewById<ImageButton>(R.id.btn_compartir)

        val animacion = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.boton_home_animado
        )

        btnGirar.startAnimation(animacion)

        btnCalificar.setOnClickListener {
            animateButton(btnCalificar) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    "https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es".toUri()
                )

                startActivity(intent)
            }
        }

        btnVolumen.setOnClickListener {
            animateButton(btnVolumen) {
                Log.d("Toolbar", "Volumen")
            }
        }

        btnInstrucciones.setOnClickListener {
            animateButton(btnInstrucciones) {
                findNavController().navigate(
                    R.id.action_home_to_instructions
                )
            }
        }

        btnRetos.setOnClickListener {
            animateButton(btnRetos) {
                findNavController().navigate(
                    R.id.action_home_to_challenges
                )
            }
        }

        btnCompartir.setOnClickListener {
            animateButton(btnCompartir) {
                shareApp()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    private fun shareApp() {

        val shareText = """
        App pico botella
        
        ¡Solo los valientes lo juegan!
        
        Descárgala aquí:
        https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es
    """.trimIndent()

        val intent = Intent().apply {

            action = Intent.ACTION_SEND

            putExtra(
                Intent.EXTRA_TEXT,
                shareText
            )

            type = "text/plain"
        }

        startActivity(
            Intent.createChooser(
                intent,
                "Compartir aplicación"
            )
        )
    }

    private fun animateButton(
        button: ImageButton,
        action: () -> Unit
    ) {

        button.animate()
            .scaleX(0.85f)
            .scaleY(0.85f)
            .setDuration(75)
            .withEndAction {

                button.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(75)
                    .withEndAction {

                        action()

                    }
                    .start()

            }
            .start()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}
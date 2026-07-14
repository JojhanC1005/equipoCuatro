package com.example.pico_botella_grupo4.view.fragment

import android.graphics.Color
import android.widget.Button
import com.bumptech.glide.Glide
import android.os.Bundle
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
import androidx.fragment.app.viewModels
import com.example.pico_botella_grupo4.viewmodel.HomeViewModel
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import kotlin.random.Random
import android.os.CountDownTimer
import android.widget.TextView
import android.app.AlertDialog
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import com.example.pico_botella_grupo4.data.DatabaseProvider
import com.example.pico_botella_grupo4.model.Challenge
import com.example.pico_botella_grupo4.model.Pokemon
import com.example.pico_botella_grupo4.viewmodel.ChallengeViewModel
import com.example.pico_botella_grupo4.viewmodel.PokemonViewModel
import kotlinx.coroutines.launch
import androidx.core.graphics.drawable.toDrawable
import com.example.pico_botella_grupo4.repository.ChallengeRepository
import com.example.pico_botella_grupo4.viewmodel.ChallengeViewModelFactory

class HomeFragment : Fragment() {
    private val pokemonViewModel: PokemonViewModel by viewModels()

    private lateinit var challengeViewModel: ChallengeViewModel
    private var mediaPlayer: MediaPlayer? = null

    private var bottleSoundPlayer: MediaPlayer? = null
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var botellaJuego: ImageView

    private lateinit var txtContador: TextView
    private var currentBottleRotation = 0f
    private var isGameRunning = false
    private var shouldResumeMusicAfterChallenge = false

    private lateinit var btnGirar: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = DatabaseProvider
            .getDatabase(requireContext())
            .challengeDao()

        val repository = ChallengeRepository(dao)

        val factory = ChallengeViewModelFactory(repository)

        challengeViewModel = ViewModelProvider(
            this,
            factory
        )[ChallengeViewModel::class.java]

        // Crear y configurar mediaPlayer para la música del juego
        setupMediaPlayer()

        setupBottleSoundPlayer()

        btnGirar = view.findViewById(R.id.btn_girar_botella)
        val btnCalificar = view.findViewById<ImageButton>(R.id.btn_calificar)
        val btnVolumen = view.findViewById<ImageButton>(R.id.btn_volumen)
        val btnInstrucciones = view.findViewById<ImageButton>(R.id.btn_instrucciones)
        val btnRetos = view.findViewById<ImageButton>(R.id.btn_retos)
        val btnCompartir = view.findViewById<ImageButton>(R.id.btn_compartir)
        botellaJuego = view.findViewById(R.id.botella_juego)
        txtContador = view.findViewById(R.id.txt_contador)
        txtContador.visibility = View.GONE


        // Configurar observer sobre el botón de volumen para controlar música
        setUpObservers(btnVolumen)

        // Configurar listeners para funcionalidad de los botones
        setUpListeners(
            btnCalificar,
            btnVolumen,
            btnInstrucciones,
            btnRetos,
            btnCompartir
        )

        btnGirar.setOnClickListener {
            spinBottle()
        }

        // Iniciar animación del botón de girar
        startDynamicButton(btnGirar)
    }

    override fun onDestroyView() {
        mediaPlayer?.release()
        mediaPlayer = null
        bottleSoundPlayer?.release()
        bottleSoundPlayer = null
        super.onDestroyView()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
        bottleSoundPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.soundEnabled.value == true && !isGameRunning) {
            mediaPlayer?.start()
        }
    }

    // Funciones auxiliares
    private fun setupMediaPlayer() {

        mediaPlayer = MediaPlayer.create(
            requireContext(),
            R.raw.musica_fondo
        )

        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    private fun setupBottleSoundPlayer() {
        bottleSoundPlayer = MediaPlayer.create(
            requireContext(),
            R.raw.sonido_botella
        )

        bottleSoundPlayer?.isLooping = true
    }

    private fun setUpObservers(
        btnVolumen : ImageButton
    ) {
        viewModel.soundEnabled.observe(
            viewLifecycleOwner
        ) { enabled ->

            if(enabled){
                btnVolumen.setImageResource(
                    R.drawable.icono_volume_on
                )

                if (mediaPlayer?.isPlaying == false && !isGameRunning) {
                    mediaPlayer?.start()
                }
            }
            else {
                btnVolumen.setImageResource(
                    R.drawable.icono_volume_off
                )

                if(mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                }
            }
        }
    }

    private fun setUpListeners(
        btnCalificar: ImageButton,
        btnVolumen: ImageButton,
        btnInstrucciones: ImageButton,
        btnRetos: ImageButton,
        btnCompartir: ImageButton
    ) {

        btnCalificar.setOnClickListener {
            // Dirige a la app de nequi en la play store
            animateButton(btnCalificar) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    "https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es".toUri()
                )

                startActivity(intent)
            }
        }

        btnVolumen.setOnClickListener {
            // Activa y desactiva el sonido del juego
            animateButton(btnVolumen) {
                viewModel.toggleSound()
            }
        }

        btnInstrucciones.setOnClickListener {
            // Dirige al apartado de instrucciones del juego
            animateButton(btnInstrucciones) {
                findNavController().navigate(
                    R.id.action_home_to_instructions
                )
            }
        }

        btnRetos.setOnClickListener {
            // Dirige al apartado de retos del juego
            animateButton(btnRetos) {
                findNavController().navigate(
                    R.id.action_home_to_challenges
                )
            }
        }

        btnCompartir.setOnClickListener {
            // Permite compartir la aplicación por whatsApp u otros medios
            animateButton(btnCompartir) {
                shareApp()
            }
        }
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

    private fun startDynamicButton(
        btnGirar : ImageButton
    ) {
        val animacion = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.boton_home_animado
        )

        btnGirar.startAnimation(animacion)
    }

    private fun spinBottle() {

        if (isGameRunning) return

        isGameRunning = true

        shouldResumeMusicAfterChallenge =
            viewModel.soundEnabled.value == true && mediaPlayer?.isPlaying == true

        mediaPlayer?.pause()

        btnGirar.clearAnimation()
        btnGirar.visibility = View.GONE

        bottleSoundPlayer?.seekTo(0)
        bottleSoundPlayer?.start()

        val randomAngle = Random.nextInt(0, 360)

        val turns = Random.nextInt(6, 10) * 360

        val finalRotation = currentBottleRotation + turns + randomAngle

        val duration = Random.nextLong(3000L, 5000L)

        ObjectAnimator.ofFloat(
            botellaJuego,
            View.ROTATION,
            currentBottleRotation,
            finalRotation
        ).apply {

            this.duration = duration

            interpolator = DecelerateInterpolator()

            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {

                    currentBottleRotation = finalRotation % 360

                    botellaJuego.rotation = currentBottleRotation

                    bottleSoundPlayer?.pause()
                    bottleSoundPlayer?.seekTo(0)

                    startCountdown()
                }

            })

            start()
        }

    }

    private fun startCountdown() {

        txtContador.visibility = View.VISIBLE

        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                val seconds = (millisUntilFinished / 1000).toInt()

                txtContador.text = seconds.toString()
            }

            override fun onFinish() {

                txtContador.text = "0"

                txtContador.postDelayed({

                    viewLifecycleOwner.lifecycleScope.launch {

                        val challenge = challengeViewModel.getRandomChallenge()
                        val pokemon = pokemonViewModel.getRandomPokemon()

                        showRandomChallengeDialog(challenge, pokemon)
                    }

                }, 1000)
            }

        }.start()

    }

    private fun showRandomChallengeDialog(
        challenge: Challenge?,
        pokemon: Pokemon
    ) {

        txtContador.visibility = View.GONE

        val dialogView = layoutInflater.inflate(
            R.layout.dialog_challenge,
            null
        )

        val tvChallenge =
            dialogView.findViewById<TextView>(R.id.tvChallenge)

        val imgPokemon =
            dialogView.findViewById<ImageView>(R.id.imgPokemon)

        val btnClose =
            dialogView.findViewById<Button>(R.id.btnClose)

        Log.d("DIALOG", "Challenge recibido: $challenge")
        Log.d("DIALOG", "Pokemon recibido: ${pokemon.name}")
        Log.d("DIALOG", "Imagen: ${pokemon.img}")

        // Mostrar el reto o el mensaje si no hay retos
        if (challenge == null) {

            tvChallenge.text =
                "No hay retos disponibles. Agrega algunos retos para poder jugar."

        } else {

            tvChallenge.text = challenge.description
            Log.d("DIALOG", "Descripcion: ${challenge.description}")

        }

        // Mostrar la imagen del Pokémon
        val imageUrl = pokemon.img.replace("http://", "https://")

        Glide.with(requireContext())
            .load(imageUrl)
            .into(imgPokemon)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawable(
            Color.TRANSPARENT.toDrawable()
        )

        // Botón Cerrar
        btnClose.setOnClickListener {
            dialog.dismiss()
            finishGame()
        }

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun finishGame() {

        isGameRunning = false

        btnGirar.visibility = View.VISIBLE

        startDynamicButton(btnGirar)

        if (shouldResumeMusicAfterChallenge && viewModel.soundEnabled.value == true) {
            mediaPlayer?.start()
        }

        shouldResumeMusicAfterChallenge = false
    }

}
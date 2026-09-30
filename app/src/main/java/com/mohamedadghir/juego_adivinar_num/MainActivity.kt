package com.mohamedadghir.juego_adivinar_num

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var numeroAleatorio = (1..100).random()
    private var intentos = 0
    private var historial = ""

    private lateinit var button: Button
    private lateinit var editTextNumber: EditText
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {

            numeroAleatorio =
                savedInstanceState.getInt("numeroAleatorio")

            intentos =
                savedInstanceState.getInt("intentos")

            historial =
                savedInstanceState.getString("historial", "")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        button = findViewById(R.id.button)
        editTextNumber = findViewById(R.id.editTextNumber)
        textView = findViewById(R.id.textView)

        textView.text = "Intents: $intentos\n\n$historial"

        button.setOnClickListener {
            intentarAdivinar()
        }

        editTextNumber.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_GO ||
                actionId == EditorInfo.IME_ACTION_NEXT
            ) {

                intentarAdivinar()

                true

            } else {

                false
            }
        }
    }

    private fun intentarAdivinar() {

        val entradaText = editTextNumber.text.toString()

        if (entradaText.isEmpty()) {

            Toast.makeText(
                this,
                "Escriu un número primer!",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val numeroUsuari = entradaText.toIntOrNull()

        if (numeroUsuari == null) {

            Toast.makeText(
                this,
                "Introdueix un número vàlid",
                Toast.LENGTH_SHORT
            ).show()

            return
        }
        intentos++
        Log.i(
            "JUEGO",
            "Número introducido: $numeroUsuari"
        )

        Log.i(
            "JUEGO",
            "Número secreto: $numeroAleatorio"
        )

        if (numeroUsuari < numeroAleatorio) {

            Toast.makeText(
                this,
                "El número que buscas es MAYOR",
                Toast.LENGTH_SHORT
            ).show()

            historial +=
                "Intent $intentos: $numeroUsuari → MÉS GRAN\n"

        } else if (numeroUsuari > numeroAleatorio) {

            Toast.makeText(
                this,
                "El número que buscas es MENOR",
                Toast.LENGTH_SHORT
            ).show()

            historial +=
                "Intent $intentos: $numeroUsuari → MÉS PETIT\n"

        } else {

            // Número acertado
            historial +=
                "Intent $intentos: $numeroUsuari → CORRECTE!\n"

            Toast.makeText(
                this,
                "Has encertat!",
                Toast.LENGTH_SHORT
            ).show()

            textView.text =
                "Intents: $intentos\n\n$historial"

            editTextNumber.text.clear()

            mostrarDialogGuanyat()

            return
        }

        textView.text =
            "Intents: $intentos\n\n$historial"

        editTextNumber.text.clear()

        editTextNumber.requestFocus()
    }

    private fun mostrarDialogGuanyat() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Has encertat!")

        builder.setMessage(
            "Has necessitat $intentos intents."
        )

        builder.setPositiveButton("Jugar de nou") { _, _ ->

            reiniciarJoc()
        }

        builder.show()
    }

    private fun reiniciarJoc() {
        numeroAleatorio = (1..100).random()

        intentos = 0

        historial = ""

        textView.text = "Intents: 0"

        editTextNumber.text.clear()

        editTextNumber.requestFocus()

        Log.i(
            "JUEGO",
            "Nova partida. Número secret: $numeroAleatorio"
        )
    }


    override fun onSaveInstanceState(outState: Bundle) {

        super.onSaveInstanceState(outState)

        outState.putInt(
            "numeroAleatorio",
            numeroAleatorio
        )

        outState.putInt(
            "intentos",
            intentos
        )

        outState.putString(
            "historial",
            historial
        )
    }
}

package com.mohamedadghir.juego_adivinar_num

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val b = findViewById<Button>(R.id.button)
        val t = findViewById<TextView>(R.id.textView)
        val t_n = findViewById<EditText>(R.id.editTextNumber)

        t.text = "Intents: 0"

        b.setOnClickListener {
            val entradaText = t_n.text.toString()

            if (entradaText.isNotEmpty()) {
                val numeroUsuari = entradaText.toInt()
                intentos++

                if (numeroUsuari < numeroAleatorio) {
                    t.text = "Intents: $intentos\nEl número secret és MÉS GRAN"
                } else if (numeroUsuari > numeroAleatorio) {
                    t.text = "Intents: $intentos\nEl número secret és MÉS PETIT"
                } else {
                    t.text = "Intents: $intentos\nHas encertat el número!"
                    mostrarDialogGuanyat()
                }

                t_n.text.clear()
            } else {
                Toast.makeText(this, "Escriu un número primer!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarDialogGuanyat() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Has encertat!")
        builder.setMessage("Has necessitat $intentos intents.")
        builder.setPositiveButton("Jugar de nou", null)
        builder.show()

        reiniciarJoc()
    }

    private fun reiniciarJoc() {
        numeroAleatorio = (1..100).random()
        intentos = 0
    }
}
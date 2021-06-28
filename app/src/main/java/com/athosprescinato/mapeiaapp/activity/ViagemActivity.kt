package com.athosprescinato.mapeiaapp.activity

import androidx.appcompat.app.AppCompatActivity
import kotlin.math.round
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.athosprescinato.mapeiaapp.R
import com.athosprescinato.mapeiaapp.helper.DBHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_viagem.*
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.math.RoundingMode


class ViagemActivity : AppCompatActivity() {

    private val dbHandler = DBHelper(this, null)
    lateinit var viagem: EditText
    lateinit var distancia: EditText
    lateinit var combustivel: EditText
    lateinit var autonomia: EditText
    var totalViagem: Float = 0.00f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viagem)

        viagem = findViewById(R.id.editNomeViagem)
        distancia = findViewById(R.id.editDistancia)
        combustivel = findViewById(R.id.editCombustivel)
        autonomia = findViewById(R.id.editAutonomia)


    }

    fun calcular(v: View) {
        if (validacao()) {
            if (editAutonomia.editableText.toString() != "0") {
                totalViagem =
                    round((distancia.editableText.toString().toFloat() * combustivel.editableText.toString().toFloat()) / autonomia.editableText.toString().toFloat())
                textGastoEstimado.text = "Gasto estimado da viagem: \n R$: " + totalViagem
            } else {
                Toast.makeText(this, "Autonomia não pode ser zero", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validacao(): Boolean {
        return (editDistancia.editableText.toString() != "" && editAutonomia.editableText.toString() != "" && editCombustivel.editableText.toString() != "")
    }

    fun salvarViagem(v: View) {

        if (validacao()) {
            var stringViagem: String = viagem.editableText.toString()
            var floatDistancia: Float = distancia.editableText.toString().toFloat()
            var floatCombustivel: Float = combustivel.editableText.toString().toFloat()
            var floatAutonomia: Float = autonomia.editableText.toString().toFloat()
            var floatTotal: Float = totalViagem

            dbHandler.insertRow(
                stringViagem,
                floatDistancia,
                floatCombustivel,
                floatAutonomia,
                floatTotal
            )
            Toast.makeText(this, "Viagem Salva!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()

        }

    }

}



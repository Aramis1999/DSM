package com.example.menukotlin

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.menukotlin.datos.PersonSalary

class AdaptadorPersonaSalario(private val context: Activity, var personasSalario: List<PersonSalary>) :
    ArrayAdapter<PersonSalary?>(context, R.layout.activity_adaptador_persona_salario, personasSalario) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        // Método invocado tantas veces como elementos tenga la coleccion personas
        // para formar a cada item que se visualizara en la lista personalizada
        val layoutInflater = context.layoutInflater
        var rowview: View? = null
        // optimizando las diversas llamadas que se realizan a este método
        // pues a partir de la segunda llamada el objeto view ya viene formado
        // y no sera necesario hacer el proceso de "inflado" que conlleva tiempo y
        // desgaste de bateria del dispositivo
        rowview = view ?: layoutInflater.inflate(R.layout.activity_adaptador_persona_salario, null)
        val tvNombre = rowview!!.findViewById<TextView>(R.id.tvNombre)
        val tvDUI = rowview.findViewById<TextView>(R.id.tvDUI)
        val tvSalarioDet = rowview.findViewById<TextView>(R.id.tvSalarioDet)
        tvNombre.text = "NOMBRE : " + personasSalario[position].nombre
        tvDUI.text = "SALARIO : " + personasSalario[position].dui.toString().toFloat()
        var salario = personasSalario[position].dui.toString().toFloat()
        var resultado = (salario - ((salario * 0.03) + (salario * 0.04) + (salario * 0.05)))
        tvSalarioDet.text = "SALARIO DET : " + resultado
        return rowview
    }
}

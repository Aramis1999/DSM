package com.example.dsm_practico01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView

class Notas : AppCompatActivity() {

    lateinit var n1 : EditText
    lateinit var n2 : EditText
    lateinit var n3 : EditText
    lateinit var n4 : EditText
    lateinit var n5 : EditText
    lateinit var res : TextView
    lateinit var pas : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        n1 = this.findViewById((R.id.editNota1))
        n2 = this.findViewById((R.id.editNota2))
        n3 = this.findViewById((R.id.editNota3))
        n4 = this.findViewById((R.id.editNota4))
        n5 = this.findViewById((R.id.editNota5))
        res = this.findViewById((R.id.textNota))
        pas = this.findViewById((R.id.textPaso))

    }

    fun promedio(view: View){
        var nota1 = n1.text.toString().toFloat()
        var nota2 = n2.text.toString().toFloat()
        var nota3 = n3.text.toString().toFloat()
        var nota4 = n4.text.toString().toFloat()
        var nota5 = n5.text.toString().toFloat()
        var resultado = (nota1 + nota2 + nota3 + nota4 +nota5) / 5
        res.setText("Nota final: " + resultado)
        if (resultado >= 6){
            pas.setText("Aprobado")
        }else{
            pas.setText("Reprobado")
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_notas -> startActivity(Intent(this,Notas::class.java))
            R.id.action_salario -> startActivity(Intent(this,Salario::class.java))
            R.id.action_calculadora -> startActivity(Intent(this,Calculadora::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
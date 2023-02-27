package com.example.dsm_practico01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView


class Salario : AppCompatActivity() {

    lateinit var sal : EditText
    lateinit var res : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salario)

        sal = this.findViewById((R.id.editSalario))
        res = this.findViewById((R.id.textResultado))
    }

    fun salarioneto(view: View){
        var salario = sal.text.toString().toFloat()
        var resultado = (salario - ((salario * 0.03) + (salario * 0.04) + (salario * 0.05)))
        res.setText("Salario neto: $" + resultado.toString())
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
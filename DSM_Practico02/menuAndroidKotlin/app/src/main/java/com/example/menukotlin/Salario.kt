package com.example.menukotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Salario : AppCompatActivity() {

    lateinit var sal : EditText
    lateinit var name : EditText
    lateinit var res : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salario)

        sal = this.findViewById((R.id.editSalario))
        name = this.findViewById((R.id.editNombre))
        res = this.findViewById((R.id.textResultado))
    }

    fun salarioneto(view: View){
        var salario = sal.text.toString().toFloat()
        var nombre = name.text.toString()
        var resultado = (salario - ((salario * 0.03) + (salario * 0.04) + (salario * 0.05)))
        res.setText("Salario neto: $" + resultado.toString() + nombre)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuopciones, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.opcion1) {
            Toast.makeText(this, "Se seleccionó la primer opción", Toast.LENGTH_LONG).show()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        if (id == R.id.opcion2) {
            Toast.makeText(this, "Se seleccionó proyecto splash", Toast.LENGTH_LONG).show()
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }
        if (id == R.id.opcion3) {
            Toast.makeText(this, "Se seleccionó proyecto salario", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Salario::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
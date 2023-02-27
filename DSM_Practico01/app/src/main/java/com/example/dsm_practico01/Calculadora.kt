package com.example.dsm_practico01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText

class Calculadora : AppCompatActivity() {

    lateinit var num1 : EditText
    lateinit var num2 : EditText
    lateinit var res : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)

        num1 = this.findViewById((R.id.editSalario))
        num2 = this.findViewById((R.id.editNum2))
        res = this.findViewById((R.id.editRes))
    }



    fun sumar(view: View){
        var numero1 = num1.text.toString().toFloat()
        var numero2 = num2.text.toString().toFloat()
        var resultado = numero1 + numero2
        res.setText(resultado.toString())
    }

    fun restar(view: View){
        var numero1 = num1.text.toString().toFloat()
        var numero2 = num2.text.toString().toFloat()
        var resultado = numero1 - numero2
        res.setText(resultado.toString())
    }

    fun multiplicar(view: View){
        var numero1 = num1.text.toString().toFloat()
        var numero2 = num2.text.toString().toFloat()
        var resultado = numero1 * numero2
        res.setText(resultado.toString())
    }

    fun dividir(view: View){
        var numero1 = num1.text.toString().toFloat()
        var numero2 = num2.text.toString().toFloat()
        var resultado = numero1 / numero2
        res.setText(resultado.toString())
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
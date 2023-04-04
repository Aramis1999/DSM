package com.example.menukotlin.datos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PersonSalary{
    fun key(key: String?) {
    }

    var dui: String? = null
    var nombre: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(dui: String?, nombre: String?) {
        this.dui = dui
        this.nombre = nombre
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "dui" to dui,
            "nombre" to nombre,
            "key" to key,
            "per" to per
        )
    }
}
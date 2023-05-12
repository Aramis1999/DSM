package edu.udb.retrofitappcrud

import retrofit2.Call
import retrofit2.http.*

interface UsuarioApi {

    @POST("login.php")
    fun loginUser(@Body usuario: Usuario): Call<Usuario>

}
package edu.udb.retrofitappcrud

import retrofit2.Call
import retrofit2.http.*

interface MaestroApi {

    @GET("maestro.php")
    fun obtenerMaestros(): Call<List<Maestro>>

    @GET("maestro.php?id={id}")
    fun obtenerMaestroPorId(@Path("id") id: Int): Call<Maestro>

    @POST("maestro.php")
    fun crearMaestro(@Body maestro: Maestro): Call<Maestro>

    @POST("update-maestro.php")
    fun actualizarMaestro(@Body maestro: Maestro): Call<Maestro>

    @POST("update-maestro.php")
    fun eliminarMaestro(@Body maestro: Maestro): Call<Void>
}
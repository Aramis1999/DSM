package edu.udb.retrofitappcrud

import retrofit2.Call
import retrofit2.http.*

interface AlumnoApi {

    @GET("alumno.php")
    fun obtenerAlumnos(): Call<List<Alumno>>

    @GET("alumno.php?id={id}")
    fun obtenerAlumnoPorId(@Path("id") id: Int): Call<Alumno>

    @POST("alumno.php")
    fun crearAlumno(@Body alumno: Alumno): Call<Alumno>

    @POST("update-alumno.php")
    fun actualizarAlumno(@Body alumno: Alumno): Call<Alumno>

    @POST("update-alumno.php")
    fun eliminarAlumno(@Body alumno: Alumno): Call<Void>
}
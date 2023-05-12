package edu.udb.retrofitappcrud

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityMaestro : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MaestroAdapter
    private lateinit var api: MaestroApi

    // Obtener las credenciales de autenticación
    val auth_username = "admin"
    val auth_password = "admin123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro)

        val fab_agregar: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_agregarMaestro)

        recyclerView = findViewById(R.id.recyclerViewMaestro)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Crea un cliente OkHttpClient con un interceptor que agrega las credenciales de autenticación
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", Credentials.basic(auth_username, auth_password))
                    .build()
                chain.proceed(request)
            }
            .build()

        // Crea una instancia de Retrofit con el cliente OkHttpClient
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-rest-intento.000webhostapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        // Crea una instancia del servicio que utiliza la autenticación HTTP básica
        api = retrofit.create(MaestroApi::class.java)

        cargarDatos(api)

        // Cuando el usuario quiere agregar un nuevo registro
        fab_agregar.setOnClickListener(View.OnClickListener {
            val i = Intent(getBaseContext(), CrearMaestroActivity::class.java)
            i.putExtra("auth_username", auth_username)
            i.putExtra("auth_password", auth_password)
            startActivity(i)
        })
    }

    override fun onResume() {
        super.onResume()
        cargarDatos(api)
    }

    private fun cargarDatos(api: MaestroApi) {
        val call = api.obtenerMaestros()
        call.enqueue(object : Callback<List<Maestro>> {
            override fun onResponse(call: Call<List<Maestro>>, response: Response<List<Maestro>>) {
                if (response.isSuccessful) {
                    val maestros = response.body()
                    if (maestros != null) {
                        adapter = MaestroAdapter(maestros)
                        recyclerView.adapter = adapter

                        // Establecemos el escuchador de clics en el adaptador
                        adapter.setOnItemClickListener(object : MaestroAdapter.OnItemClickListener {
                            override fun onItemClick(maestro: Maestro) {
                                val opciones = arrayOf("Modificar Maestro", "Eliminar Maestro")

                                AlertDialog.Builder(this@MainActivityMaestro)
                                    .setTitle(maestro.nombre)
                                    .setItems(opciones) { dialog, index ->
                                        when (index) {
                                            0 -> Modificar(maestro)
                                            1 -> eliminarMaestro(maestro, api)
                                        }
                                    }
                                    .setNegativeButton("Cancelar", null)
                                    .show()
                            }
                        })
                    }
                } else {
                    val error = response.errorBody()?.string()
                    Log.e("API", "Error al obtener los maestros: $error")
                    Toast.makeText(
                        this@MainActivityMaestro,
                        "Error al obtener los maestros 1",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Maestro>>, t: Throwable) {
                Log.e("API", "Error al obtener los maestros: ${t.message}")
                Toast.makeText(
                    this@MainActivityMaestro,
                    "Error al obtener los maestros 2",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun Modificar(maestro: Maestro) {
        // Creamos un intent para ir a la actividad de actualización de maestros
        val i = Intent(getBaseContext(), ActualizarMaestroActivity::class.java)
        // Pasamos el ID del maestro seleccionado a la actividad de actualización
        i.putExtra("maestro_id", maestro.id)
        i.putExtra("nombre", maestro.nombre)
        i.putExtra("apellido", maestro.apellido)
        i.putExtra("edad", maestro.edad)
        // Iniciamos la actividad de actualización de maestro
        startActivity(i)
    }

    private fun eliminarMaestro(maestro: Maestro, api: MaestroApi) {
        val maestroTMP = Maestro(maestro.id,"", "", -987)
        Log.e("API", "id : $maestro")
        val llamada = api.eliminarMaestro(maestroTMP)
        llamada.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivityMaestro, "Maestro eliminado", Toast.LENGTH_SHORT).show()
                    cargarDatos(api)
                } else {
                    val error = response.errorBody()?.string()
                    Log.e("API", "Error al eliminar maestro : $error")
                    Toast.makeText(this@MainActivityMaestro, "Error al eliminar maestro 1", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("API", "Error al eliminar maestro : $t")
                Toast.makeText(this@MainActivityMaestro, "Error al eliminar maestro 2", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuopciones, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.opcion1) {
            Toast.makeText(this, "Se seleccionó el CRUD Alumnos", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        if (id == R.id.opcion2) {
            Toast.makeText(this, "Se seleccionó el CRUD Maestros", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivityMaestro::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
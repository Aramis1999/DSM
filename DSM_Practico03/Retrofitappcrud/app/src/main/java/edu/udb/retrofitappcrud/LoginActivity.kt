package edu.udb.retrofitappcrud

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var usuarioEditText: EditText
    private lateinit var claveEditText: EditText
    private lateinit var loginButton: Button

    // Obtener las credenciales de autenticación
    var auth_username = "admin"
    var auth_password = "admin123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Obtención de datos que envia actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            auth_username = datos.getString("auth_username").toString()
            auth_password = datos.getString("auth_password").toString()
        }

        usuarioEditText = findViewById(R.id.user)
        claveEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.login)

        loginButton.setOnClickListener {
            val usuarioE = usuarioEditText.text.toString()
            val clave = claveEditText.text.toString()

            val usuario = Usuario(usuario = usuarioE,clave)
            Log.e("API", "auth_username: $auth_username")
            Log.e("API", "auth_password: $auth_password")

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
            val api = retrofit.create(UsuarioApi::class.java)

            api.loginUser(usuario).enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    Log.e("LOGIN", "Respuesta: ${response.body()}");
                    if (response.isSuccessful) {
                        val usuarios = response.body()
                        if (usuarios != null) {
                            Toast.makeText(this@LoginActivity, "LOGIN EXITOSO", Toast.LENGTH_SHORT).show()
                            val i = Intent(getBaseContext(), MainActivity::class.java)
                            startActivity(i)
                        }else{
                            Toast.makeText(this@LoginActivity, "El usuario o la contraseña son incorrectos", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        val error = response.errorBody()?.string()
                        Log.e("API", "Error al ingresar: $error")
                        Toast.makeText(this@LoginActivity, "Error al ingresar 2 $error", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Log.e("LOGIN FAILURE", "Error al ingresar: $t")
                    Toast.makeText(this@LoginActivity, "Error al ingresar 3", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
package com.joselop.consumoapi

import UserAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joselop.consumoapi.model.network.RandomUserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var randomUserService: RandomUserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        randomUserService = retrofit.create(RandomUserService::class.java)

        // Llamada para cargar los usuarios usando corrutinas
        GlobalScope.launch(Dispatchers.Main) {
            val response = randomUserService.getUsers()
            if (response.isSuccessful) {
                response.body()?.results?.let { users ->
                    userAdapter = UserAdapter(users) { user ->
                        // Navegar a la nueva actividad con los detalles del usuario
                        val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                        intent.putExtra("user", user)  // El objeto User debe estar correctamente parcelado ahora
                        startActivity(intent)

                    }
                    recyclerView.adapter = userAdapter
                }
            }
        }
    }
}

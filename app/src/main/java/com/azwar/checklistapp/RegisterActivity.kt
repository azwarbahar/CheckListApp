package com.azwar.checklistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.azwar.checklistapp.data.Responses
import com.azwar.checklistapp.databinding.ActivityRegisterBinding
import com.azwar.checklistapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiClient = ApiClient()

        binding.tvLogin.setOnClickListener { finish() }

        binding.btnRegister.setOnClickListener {
            var email = binding.etEmail.text
            var username = binding.etUsername.text
            var password = binding.etPassword.text
            startRegister(email, username, password)
        }

    }

    private fun startRegister(email: Editable?, username: Editable?, password: Editable?) {

        apiClient.getApiService()
            .register(
                email.toString(),
                password.toString(),
                username.toString()
            )
            .enqueue(object : Callback<Responses.AuthResponse> {
                override fun onFailure(
                    call: Call<Responses.AuthResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<Responses.AuthResponse>,
                    response: Response<Responses.AuthResponse>
                ) {
                    val pesanRespon = response.message()
                    val message = response.body()?.message
                    val kode = response.body()?.statusCode
                    Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()

                    binding.etEmail.setText("")
                    binding.etUsername.setText("")
                    binding.etPassword.setText("")
                }
            })


    }
}
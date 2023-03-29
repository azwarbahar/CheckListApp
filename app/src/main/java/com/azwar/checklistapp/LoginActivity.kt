package com.azwar.checklistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.azwar.checklistapp.data.Responses
import com.azwar.checklistapp.data.TokenModel
import com.azwar.checklistapp.databinding.ActivityLoginBinding
import com.azwar.checklistapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    private lateinit var tokenModel: TokenModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            var username = binding.etUsername.text
            var password = binding.etPassword.text
            startLogin(username, password)
        }

    }

    private fun startLogin(username: Editable?, password: Editable?) {


        apiClient.getApiService()
            .login(
                password.toString(),
                username.toString()
            )
            .enqueue(object : Callback<Responses.AuthResponse> {
                override fun onFailure(
                    call: Call<Responses.AuthResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<Responses.AuthResponse>,
                    response: Response<Responses.AuthResponse>
                ) {
                    val pesanRespon = response.message()
                    val message = response.body()?.message
                    val kode = response.body()?.statusCode
                    tokenModel = response.body()!!.data
                    sessionManager.saveAuthToken(tokenModel.token)
                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            })


    }
}
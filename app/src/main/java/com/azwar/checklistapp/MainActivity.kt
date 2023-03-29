package com.azwar.checklistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.app.AlertDialog
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azwar.checklistapp.adapter.KendaraanAdapter
import com.azwar.checklistapp.data.KendaraanModel
import com.azwar.checklistapp.data.Responses
import com.azwar.checklistapp.databinding.ActivityMainBinding
import com.azwar.checklistapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager

    private lateinit var apiClient: ApiClient
    private lateinit var kendaraanAdapter: KendaraanAdapter
    private lateinit var kendaraans: List<KendaraanModel>
    private lateinit var kendaraan: KendaraanModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        getChacklist()

        binding.fabAdd.setOnClickListener {
            showDialogInput()
        }

    }

    private fun getChacklist() {

        apiClient.getApiService().getAllChacklist("Bearer " + sessionManager.fetchAuthToken())
            .enqueue(object : Callback<Responses.GetChecklistResponse> {
                override fun onFailure(call: Call<Responses.GetChecklistResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT)
                        .show()
                    Log.e("MainActivity", "Error : " + t.message)
                }

                override fun onResponse(
                    call: Call<Responses.GetChecklistResponse>,
                    response: Response<Responses.GetChecklistResponse>
                ) {
                    val pesanRespon = response.message()
                    val message = response.body()?.message
                    val kode = response.body()?.statusCode
                    if (kode!!.equals("2000")) {
                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                    } else {
                        kendaraans = response.body()!!.data
                        val layoutManager: RecyclerView.LayoutManager =
                            LinearLayoutManager(this@MainActivity)
                        binding.rvList.layoutManager = layoutManager
                        kendaraanAdapter = KendaraanAdapter(kendaraans)
                        binding.rvList.adapter = kendaraanAdapter
                    }
                }
            })

    }

    private fun createChacklist(inputText: String) {

        apiClient.getApiService().createChacklist(token = "Bearer " + sessionManager.fetchAuthToken(), inputText)
            .enqueue(object : Callback<Responses.SaveChecklistResponse> {
                override fun onFailure(call: Call<Responses.SaveChecklistResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Gagal get data all", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<Responses.SaveChecklistResponse>,
                    response: Response<Responses.SaveChecklistResponse>
                ) {
                    val pesanRespon = response.message()
                    val message = response.body()?.message
                    val kode = response.body()?.statusCode
                    kendaraan = response.body()!!.data
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                    getChacklist()
                }
            })

    }

    private fun showDialogInput() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle(title)

        val view = layoutInflater.inflate(R.layout.dialog_input_single, null)
        val input = view.findViewById<EditText>(R.id.et_value)

        builder.setView(view)

        builder.setPositiveButton("Simpan") { dialog, which ->
            // Action for "OK".
            val inputText = input.text.toString()
            createChacklist(inputText)
        }

        builder.setNegativeButton("Batal") { dialog, which ->
            // Action for "Cancel".
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()

    }

}
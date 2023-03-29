package com.azwar.checklistapp

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azwar.checklistapp.adapter.KendaraanAdapter
import com.azwar.checklistapp.adapter.KendaraanItemAdapter
import com.azwar.checklistapp.data.KendaraanItemModel
import com.azwar.checklistapp.data.KendaraanModel
import com.azwar.checklistapp.data.Responses
import com.azwar.checklistapp.databinding.ActivityMain2Binding
import com.azwar.checklistapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {


    private lateinit var kendaraan: KendaraanModel

    private lateinit var binding: ActivityMain2Binding
    private lateinit var apiClient: ApiClient
    private lateinit var kendaraanItemAdapter: KendaraanItemAdapter
    private lateinit var kendaraanItems: List<KendaraanItemModel>
    private lateinit var kendaraanItem: KendaraanItemModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        kendaraan = intent.getParcelableExtra("kendaraan")!!
        apiClient = ApiClient()
        getChacklist()
        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.tvToolbar.setText(kendaraan.name)

        binding.tvHapus.setOnClickListener {
            DeleteChacklist(kendaraan.id)
        }

        binding.fabAdd.setOnClickListener {
            showDialogInput()
        }
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
            createChacklistItem(inputText)
        }

        builder.setNegativeButton("Batal") { dialog, which ->
            // Action for "Cancel".
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()

    }

    private fun createChacklistItem(inputText: String) {

        apiClient.getApiService()
            .createChacklistItem(token = "Bearer " + Constants.TOKEN, kendaraan.id, inputText)
            .enqueue(object : Callback<Responses.SaveChecklistItemResponse> {
                override fun onFailure(
                    call: Call<Responses.SaveChecklistItemResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(this@MainActivity2, "Gagal get data all", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<Responses.SaveChecklistItemResponse>,
                    response: Response<Responses.SaveChecklistItemResponse>
                ) {
                    val pesanRespon = response.message()
                    val message = response.body()?.message
                    val kode = response.body()?.statusCode
                    kendaraanItem = response.body()!!.data
                    Toast.makeText(this@MainActivity2, message, Toast.LENGTH_SHORT).show()
                    getChacklist()
                }
            })

    }

    private fun DeleteChacklist(id: Int?) {
        var apiClient: ApiClient
        apiClient = ApiClient()
        apiClient.getApiService().deleteChacklist(token = "Bearer " + Constants.TOKEN, id)
            .enqueue(object : Callback<Responses.DeleteChecklistResponse> {
                override fun onFailure(
                    call: Call<Responses.DeleteChecklistResponse>, t: Throwable
                ) {
                }

                override fun onResponse(
                    call: Call<Responses.DeleteChecklistResponse>,
                    response: Response<Responses.DeleteChecklistResponse>
                ) {
                    val pesanRespon = response.message()
                    val message = response.body()?.message
                    val kode = response.body()?.statusCode
                    Toast.makeText(this@MainActivity2, message, Toast.LENGTH_SHORT).show()
                    finish()
                }
            })

    }

    private fun getChacklist() {

        apiClient.getApiService()
            .getAllChacklistItem(token = "Bearer " + Constants.TOKEN, kendaraan.id)
            .enqueue(object : Callback<Responses.GetChecklistItemResponse> {
                override fun onFailure(
                    call: Call<Responses.GetChecklistItemResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        this@MainActivity2,
                        "Gagal get data item all",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onResponse(
                    call: Call<Responses.GetChecklistItemResponse>,
                    response: Response<Responses.GetChecklistItemResponse>
                ) {
                    val pesanRespon = response.message()
                    val message = response.body()?.message
                    val kode = response.body()?.statusCode
                    if (kode!!.equals("2000")) {
                        Toast.makeText(this@MainActivity2, message, Toast.LENGTH_SHORT).show()
                    } else {
                        kendaraanItems = response.body()!!.data
                        val layoutManager: RecyclerView.LayoutManager =
                            LinearLayoutManager(this@MainActivity2)
                        binding.rvList.layoutManager = layoutManager
                        kendaraanItemAdapter = KendaraanItemAdapter(kendaraanItems, kendaraan.id!!)
                        binding.rvList.adapter = kendaraanItemAdapter
                    }
                }
            })

    }
}
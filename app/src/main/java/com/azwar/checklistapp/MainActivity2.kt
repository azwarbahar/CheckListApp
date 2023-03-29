package com.azwar.checklistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
package com.azwar.checklistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.azwar.checklistapp.data.KendaraanItemModel
import com.azwar.checklistapp.data.Responses
import com.azwar.checklistapp.databinding.ActivityDetailItemBinding
import com.azwar.checklistapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailItemBinding
    private lateinit var apiClient: ApiClient
    private lateinit var kendaraanItem: KendaraanItemModel
    private var checklistId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiClient = ApiClient()
        binding.imgBack.setOnClickListener {
            finish()
        }

        kendaraanItem = intent.getParcelableExtra("kendaraanItem")!!
        checklistId = intent.getIntExtra("kendaraanId", 0)
        initData(kendaraanItem)

        binding.tvToolbar.setText(kendaraanItem.name)

        binding.tvHapus.setOnClickListener {
            DeleteChacklistItem(kendaraanItem.id, checklistId!!)
        }

        binding.imgUbahStatus.setOnClickListener {
            updateStatu()
        }

    }

    private fun updateStatu() {

        apiClient.getApiService()
            .updateItemStatus(token = "Bearer " + Constants.TOKEN, checklistId, kendaraanItem.id)
            .enqueue(object : Callback<Responses.UpdateChecklistItemResponse> {
                override fun onFailure(
                    call: Call<Responses.UpdateChecklistItemResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(this@DetailItemActivity, "Gagal get data all", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<Responses.UpdateChecklistItemResponse>,
                    response: Response<Responses.UpdateChecklistItemResponse>
                ) {
                    val pesanRespon = response.message()
                    val message = response.body()?.message
                    val kode = response.body()?.statusCode
                    kendaraanItem = response.body()!!.data
                    Toast.makeText(this@DetailItemActivity, message, Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun initData(kendaraanItem: KendaraanItemModel) {

        binding.tvNama.setText("Nama : " + kendaraanItem.name)
        binding.tvId.setText("ID : " + kendaraanItem.id)
        var status = kendaraanItem.itemCompletionStatus
        if (status == true) {
            binding.tvId.setText("Status : Aktif")
        } else {
            binding.tvId.setText("Status : Non Aktif")
        }


    }

    private fun DeleteChacklistItem(id: Int?, checklistId: Int) {
        var apiClient: ApiClient
        apiClient = ApiClient()
        apiClient.getApiService()
            .deleteChacklistItem(token = "Bearer " + Constants.TOKEN, checklistId, id)
            .enqueue(object : Callback<Responses.DeleteChecklistItemResponse> {
                override fun onFailure(
                    call: Call<Responses.DeleteChecklistItemResponse>,
                    t: Throwable
                ) {
                }

                override fun onResponse(
                    call: Call<Responses.DeleteChecklistItemResponse>,
                    response: Response<Responses.DeleteChecklistItemResponse>
                ) {
                    val pesanRespon = response.message()
                    val message = response.body()?.message
                    val kode = response.body()?.statusCode
                    Toast.makeText(this@DetailItemActivity, message, Toast.LENGTH_SHORT).show()
                }
            })

    }
}
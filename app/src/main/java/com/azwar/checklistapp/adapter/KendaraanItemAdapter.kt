package com.azwar.checklistapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.azwar.checklistapp.Constants
import com.azwar.checklistapp.data.KendaraanItemModel
import com.azwar.checklistapp.data.Responses
import com.azwar.checklistapp.databinding.ItemListBinding
import com.azwar.checklistapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KendaraanItemAdapter(private val list: List<KendaraanItemModel>, var checklistId: Int) :
    RecyclerView.Adapter<KendaraanItemAdapter.MyHolderView>() {
    class MyHolderView(private var binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(get: KendaraanItemModel, checklistId: Int) {
            with(itemView) {
                binding.tvTitle.setText(get.name.toString())

                var id = get.id

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
                        Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
                    }
                })

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderView {
        val binding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyHolderView(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyHolderView, position: Int) =
        holder.bind(list.get(position), checklistId)
}
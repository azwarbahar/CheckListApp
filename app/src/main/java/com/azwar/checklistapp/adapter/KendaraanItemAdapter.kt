package com.azwar.checklistapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.azwar.checklistapp.Constants
import com.azwar.checklistapp.DetailItemActivity
import com.azwar.checklistapp.MainActivity2
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

                itemView.setOnClickListener {

                    val intent = Intent(context, DetailItemActivity::class.java)
                    intent.putExtra("kendaraanItem", get)
                    intent.putExtra("kendaraanId", checklistId)
                    context.startActivity(intent)
                }

            }
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
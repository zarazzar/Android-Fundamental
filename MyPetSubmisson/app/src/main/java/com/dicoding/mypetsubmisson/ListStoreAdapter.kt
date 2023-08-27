package com.dicoding.mypetsubmisson

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mypetsubmisson.databinding.ItemPetstoreBinding

class ListStoreAdapter(private val listPetStore: ArrayList<PetStore>) :
    RecyclerView.Adapter<ListStoreAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: PetStore)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            ItemPetstoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listPetStore.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title, openTime, address, phoneNumber, photo) = listPetStore[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.itemPetstoreBinding.imgItemPhoto)

        holder.itemPetstoreBinding.tvItemTitle.text = title
        holder.itemPetstoreBinding.tvItemOpentime.text = openTime
        holder.itemPetstoreBinding.tvItemAddress.text = address
        holder.itemPetstoreBinding.tvItemPhonenumber.text = phoneNumber

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listPetStore[holder.adapterPosition]) }


    }

    class ListViewHolder(val itemPetstoreBinding: ItemPetstoreBinding) :
        RecyclerView.ViewHolder(itemPetstoreBinding.root) {


    }


}
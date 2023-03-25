package uz.devapp.foodexpress.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.devapp.foodexpress.R
import uz.devapp.foodexpress.databinding.CategoryItemLayoutBinding
import uz.devapp.foodexpress.databinding.CategoryTabItemLayoutBinding
import uz.devapp.foodexpress.databinding.SlideItemLayoutBinding
import uz.devapp.foodexpress.model.CategoryModel
import uz.devapp.foodexpress.model.SlideModel

interface CategoryTabAdapterCallback {
    fun onClick(item: CategoryModel)
}

class CategoryTabAdapter(val items: List<CategoryModel>, val callback: CategoryTabAdapterCallback) :
    RecyclerView.Adapter<CategoryTabAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: CategoryTabItemLayoutBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CategoryTabItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.tvName.text = item.title

        holder.itemView.setOnClickListener {
            items.forEach {
                it.active = false
            }
            item.active = true
            notifyDataSetChanged()
            callback.onClick(item)
        }

        holder.binding.tvName.setTextColor(
            if (item.active) ContextCompat.getColor(
                holder.itemView.context,
                R.color.color_accent
            ) else ContextCompat.getColor(holder.itemView.context, R.color.grey_text)
        )
    }
}
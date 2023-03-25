package uz.devapp.foodexpress.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.devapp.foodexpress.databinding.CategoryItemLayoutBinding
import uz.devapp.foodexpress.databinding.SlideItemLayoutBinding
import uz.devapp.foodexpress.model.CategoryModel
import uz.devapp.foodexpress.model.SlideModel
import uz.devapp.foodexpress.utils.loadImage

class CategoryAdapter(val items: List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: CategoryItemLayoutBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CategoryItemLayoutBinding.inflate(
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
        holder.binding.tvTitle.text = item.title
        holder.binding.img.loadImage(item.image)
    }
}
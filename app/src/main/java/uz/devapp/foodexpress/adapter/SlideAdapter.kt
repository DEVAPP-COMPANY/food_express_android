package uz.devapp.foodexpress.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import uz.devapp.foodexpress.databinding.SlideItemLayoutBinding
import uz.devapp.foodexpress.model.OfferModel
import uz.devapp.foodexpress.model.SlideModel
import uz.devapp.foodexpress.utils.Constants
import uz.devapp.foodexpress.utils.loadImage

class SlideAdapter(val items: List<OfferModel>) : RecyclerView.Adapter<SlideAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: SlideItemLayoutBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            SlideItemLayoutBinding.inflate(
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
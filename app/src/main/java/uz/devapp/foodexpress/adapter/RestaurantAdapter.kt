package uz.devapp.foodexpress.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.devapp.foodexpress.databinding.CategoryItemLayoutBinding
import uz.devapp.foodexpress.databinding.RestaurantItemLayoutBinding
import uz.devapp.foodexpress.databinding.SlideItemLayoutBinding
import uz.devapp.foodexpress.model.CategoryModel
import uz.devapp.foodexpress.model.RestaurantModel
import uz.devapp.foodexpress.model.SlideModel
import uz.devapp.foodexpress.screen.main.restaurant.detail.RestaurantDetailActivity
import uz.devapp.foodexpress.utils.Constants.EXTRA_DATA
import uz.devapp.foodexpress.utils.loadImage

class RestaurantAdapter(val items: List<RestaurantModel>) :
    RecyclerView.Adapter<RestaurantAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: RestaurantItemLayoutBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            RestaurantItemLayoutBinding.inflate(
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
        holder.binding.img.loadImage(item.main_image)
        holder.binding.tvName.text = item.name
        holder.binding.tvAddress.text = item.address
        holder.binding.tvDistance.text = "%.2f".format(item.distance)
        holder.binding.tvRating.text = item.rating.toString()
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, RestaurantDetailActivity::class.java)
            intent.putExtra(EXTRA_DATA, item)
            it.context.startActivity(intent)
        }
    }
}
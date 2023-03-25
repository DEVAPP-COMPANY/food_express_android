package uz.devapp.foodexpress.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.devapp.foodexpress.databinding.CartProductItemLayoutBinding
import uz.devapp.foodexpress.databinding.CategoryItemLayoutBinding
import uz.devapp.foodexpress.databinding.ProductItemLayoutBinding
import uz.devapp.foodexpress.databinding.SlideItemLayoutBinding
import uz.devapp.foodexpress.model.CategoryModel
import uz.devapp.foodexpress.model.ProductModel
import uz.devapp.foodexpress.model.SlideModel
import uz.devapp.foodexpress.utils.PrefUtils
import uz.devapp.foodexpress.utils.loadImage

interface CartProductAdapterCallback{
    fun onUpdate(count: Int)
}

class CartProductAdapter(val items: List<ProductModel>, val callback: CartProductAdapterCallback) :
    RecyclerView.Adapter<CartProductAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: CartProductItemLayoutBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CartProductItemLayoutBinding.inflate(
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

        holder.binding.imgAdd.setOnClickListener {
            PrefUtils.add2Cart(item.id, ++item.cartCount)
            notifyItemChanged(position)
            callback.onUpdate(item.cartCount)
        }

        holder.binding.imgMinus.setOnClickListener {
            if(item.cartCount > 0){
                PrefUtils.add2Cart(item.id, --item.cartCount)
                notifyItemChanged(position)
                callback.onUpdate(item.cartCount)
            }
        }
        holder.binding.img.loadImage(item.image)
        holder.binding.tvName.text = item.name
        holder.binding.tvPrice.text = item.price.toString() + " UZS"
        holder.binding.tvCartCount.text = item.cartCount.toString()
    }
}
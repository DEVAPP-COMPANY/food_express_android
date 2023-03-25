package uz.devapp.foodexpress.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide


fun ImageView.loadImage(url: String){
    Glide.with(this.context)
        .load(Constants.BASE_URL+url)
        .into(this)
}

fun Context.showMessage(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG)
        .show()
}
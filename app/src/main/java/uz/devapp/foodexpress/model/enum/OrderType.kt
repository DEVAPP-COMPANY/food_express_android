package uz.devapp.foodexpress.model.enum

import com.google.gson.annotations.SerializedName

enum class OrderType(value: String) {
    @SerializedName("cash")
    CASH("cash"),
    @SerializedName("card")
    CARD("card")
}
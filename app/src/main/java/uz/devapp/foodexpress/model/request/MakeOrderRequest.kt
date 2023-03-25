package uz.devapp.foodexpress.model.request

import uz.devapp.foodexpress.model.CartModel
import uz.devapp.foodexpress.model.enum.OrderType

data class MakeOrderRequest(
    var order_type: OrderType,
    var adress: String,
    var latitude: Double,
    var longitude: Double,
    var order_products: List<CartModel>
)

package uz.devapp.foodexpress.model

data class ProductModel(
    val id: Int,
    val image: String,
    val name: String,
    val price: Double,
    var cartCount: Int = 0
)

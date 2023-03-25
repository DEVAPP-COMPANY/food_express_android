package uz.devapp.foodexpress.model

data class RestaurantModel(
    val id: Int,
    val main_image: String,
    val name: String,
    val phone: String,
    val address: String,
    val distance: Double,
    val rating: Double,
): java.io.Serializable

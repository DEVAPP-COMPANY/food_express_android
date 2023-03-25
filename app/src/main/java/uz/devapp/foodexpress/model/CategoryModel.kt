package uz.devapp.foodexpress.model

data class CategoryModel(
    val id: Int,
    val image: String,
    val title: String,
    var active: Boolean = false,
)

package uz.devapp.foodexpress.model.request

data class RestaurantFilter(
    var region_id: Int = 0,
    var district_id: Int = 0,
    var category_id: Int = 0,
    var food_id: Int = 0,
    var limit: Int = 0,
    var keyword: String = "",
    var sort: String = "distance",
    var latitude: Double = 40.4618636,
    var longitude: Double = 71.7804718,
)
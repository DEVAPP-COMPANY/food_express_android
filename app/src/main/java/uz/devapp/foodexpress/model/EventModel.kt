package uz.devapp.foodexpress.model

data class EventModel<T>(
    val command: Int,
    val data: T
)

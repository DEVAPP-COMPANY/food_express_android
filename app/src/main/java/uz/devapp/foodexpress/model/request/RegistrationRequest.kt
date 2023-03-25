package uz.devapp.foodexpress.model.request

data class RegistrationRequest(
    val phone: String,
    val password: String,
    val fullname: String,
)

package uz.devapp.foodexpress.model.response

data class LoginResponse(
    val token: String,
    val fullname: String
)
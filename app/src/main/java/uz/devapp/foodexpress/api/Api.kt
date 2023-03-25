package uz.devapp.foodexpress.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import uz.devapp.foodexpress.model.*
import uz.devapp.foodexpress.model.request.LoginRequest
import uz.devapp.foodexpress.model.request.MakeOrderRequest
import uz.devapp.foodexpress.model.request.RegistrationRequest
import uz.devapp.foodexpress.model.request.RestaurantFilter
import uz.devapp.foodexpress.model.response.BaseResponse
import uz.devapp.foodexpress.model.response.FoodsByIdsRequest
import uz.devapp.foodexpress.model.response.LoginResponse
import uz.devapp.foodexpress.utils.Constants.DEVELOPER_KEY

interface Api {
    @POST("api/login")
    fun login(
        @Body request: LoginRequest,
    ): Call<BaseResponse<LoginResponse?>>

    @POST("api/registration")
    fun registration(
        @Body request: RegistrationRequest,
    ): Call<BaseResponse<LoginResponse?>>

    @GET("api/user")
    suspend fun getUser(
    ): Response<BaseResponse<UserModel?>>

    @GET("api/offers")
    suspend fun getOffers(
    ): Response<BaseResponse<List<OfferModel>?>>

    @GET("api/categories")
    suspend fun getCategories(
    ): Response<BaseResponse<List<CategoryModel>?>>

    @POST("api/restaurants")
    suspend fun getRestaurants(
        @Body request: RestaurantFilter,
    ): Response<BaseResponse<List<RestaurantModel>?>>

    @GET("api/restaurant/{restaurant_id}/foods")
    suspend fun getProducts(
        @Path("restaurant_id") id: Int,
    ): Response<BaseResponse<List<ProductModel>?>>

    @POST("api/get/food_by_ids")
    suspend fun getFoodByIds(
        @Body request: FoodsByIdsRequest,
    ): Response<BaseResponse<List<ProductModel>?>>

    @POST("api/make_order")
    suspend fun makeOrder(
        @Body request: MakeOrderRequest,
    ): Response<BaseResponse<Any>>
}
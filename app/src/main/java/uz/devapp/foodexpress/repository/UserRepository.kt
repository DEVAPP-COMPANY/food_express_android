package uz.devapp.foodexpress.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.devapp.foodexpress.api.NetworkObject
import uz.devapp.foodexpress.base.BaseRepository
import uz.devapp.foodexpress.model.request.MakeOrderRequest
import uz.devapp.foodexpress.model.request.RestaurantFilter
import uz.devapp.foodexpress.model.response.FoodsByIdsRequest
import uz.devapp.foodexpress.model.sealed.DataResult

class UserRepository : BaseRepository() {
    val api = NetworkObject.getClientInstance()

    suspend fun getUser() = withContext(Dispatchers.IO) {
        try {
            val response = api.getUser()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

    suspend fun getOffers() = withContext(Dispatchers.IO) {
        try {
            val response = api.getOffers()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

    suspend fun getCategories() = withContext(Dispatchers.IO) {
        try {
            val response = api.getCategories()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

    suspend fun getRestaurants(filter: RestaurantFilter) = withContext(Dispatchers.IO) {
        try {
            val response = api.getRestaurants(filter)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

    suspend fun getProducts(restaurantId: Int) = withContext(Dispatchers.IO) {
        try {
            val response = api.getProducts(restaurantId)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

    suspend fun getFoodsByIds(request: FoodsByIdsRequest) = withContext(Dispatchers.IO) {
        try {
            val response = api.getFoodByIds(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

    suspend fun makeOrder(request: MakeOrderRequest) = withContext(Dispatchers.IO) {
        try {
            val response = api.makeOrder(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

}
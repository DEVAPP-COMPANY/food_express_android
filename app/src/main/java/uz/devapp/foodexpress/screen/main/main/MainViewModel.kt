package uz.devapp.foodexpress.screen.main.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import uz.devapp.foodexpress.model.CategoryModel
import uz.devapp.foodexpress.model.OfferModel
import uz.devapp.foodexpress.model.RestaurantModel
import uz.devapp.foodexpress.model.request.RestaurantFilter
import uz.devapp.foodexpress.model.sealed.DataResult
import uz.devapp.foodexpress.repository.UserRepository
import uz.devapp.foodexpress.utils.PrefUtils

class MainViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _offerListLiveData = MutableLiveData<List<OfferModel>>()
    var offerListLiveData: LiveData<List<OfferModel>> = _offerListLiveData

    var _categoryListLiveData = MutableLiveData<List<CategoryModel>>()
    var categoryListLiveData: LiveData<List<CategoryModel>> = _categoryListLiveData

    var _nearbyListLiveData = MutableLiveData<List<RestaurantModel>>()
    var nearbyListLiveData: LiveData<List<RestaurantModel>> = _nearbyListLiveData

    var _topListLiveData = MutableLiveData<List<RestaurantModel>>()
    var topListLiveData: LiveData<List<RestaurantModel>> = _topListLiveData

    fun getOffers() {
        _progressLiveData.value = true
        viewModelScope.launch {
            when (val result = repository.getOffers()) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _offerListLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            when (val result = repository.getCategories()) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _categoryListLiveData.value = (result.result)
                }
            }
        }
    }

    fun getNearbyRestaurants() {
        viewModelScope.launch {
            when (val result = repository.getRestaurants(RestaurantFilter())) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _nearbyListLiveData.value = (result.result)
                }
            }
        }
    }

    fun getTopRestaurants() {
        viewModelScope.launch {
            when (val result = repository.getRestaurants(RestaurantFilter(sort = "rating"))) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _nearbyListLiveData.value = (result.result)
                }
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            when (val result = repository.getUser()) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    PrefUtils.setUser(result.result)
                }
            }
        }
    }

}
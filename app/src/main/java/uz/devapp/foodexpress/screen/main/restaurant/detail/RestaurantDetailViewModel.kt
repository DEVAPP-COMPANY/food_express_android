package uz.devapp.foodexpress.screen.main.restaurant.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.devapp.foodexpress.model.CategoryModel
import uz.devapp.foodexpress.model.ProductModel
import uz.devapp.foodexpress.model.sealed.DataResult
import uz.devapp.foodexpress.repository.UserRepository

class RestaurantDetailViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    var _categoryListLiveData = MutableLiveData<List<CategoryModel>>()
    var categoryListLiveData: LiveData<List<CategoryModel>> = _categoryListLiveData

    private var _foodListLiveData = MutableLiveData<List<ProductModel>>()
    var foodListLiveData: LiveData<List<ProductModel>> = _foodListLiveData


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


    fun getFoods(restaurantId: Int) {
        viewModelScope.launch {
            when (val result = repository.getProducts(restaurantId)) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _foodListLiveData.value = (result.result)
                }
            }
        }
    }
}
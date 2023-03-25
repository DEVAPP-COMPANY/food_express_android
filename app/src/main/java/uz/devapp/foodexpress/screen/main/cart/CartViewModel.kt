package uz.devapp.foodexpress.screen.main.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import uz.devapp.foodexpress.api.NetworkObject
import uz.devapp.foodexpress.model.OfferModel
import uz.devapp.foodexpress.model.ProductModel
import uz.devapp.foodexpress.model.response.BaseResponse
import uz.devapp.foodexpress.model.response.FoodsByIdsRequest
import uz.devapp.foodexpress.model.sealed.DataResult
import uz.devapp.foodexpress.repository.UserRepository
import uz.devapp.foodexpress.utils.PrefUtils

class CartViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _foodListLiveData = MutableLiveData<List<ProductModel>>()
    var foodListLiveData: LiveData<List<ProductModel>> = _foodListLiveData


    fun getFoods() {
        viewModelScope.launch {
            when (val result = repository.getFoodsByIds(FoodsByIdsRequest(PrefUtils.getCartList().map { it.id }))) {
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
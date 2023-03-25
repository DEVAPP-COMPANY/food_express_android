package uz.devapp.foodexpress.base

import org.greenrobot.eventbus.EventBus
import retrofit2.Response
import uz.devapp.foodexpress.model.EventModel
import uz.devapp.foodexpress.model.response.BaseResponse
import uz.devapp.foodexpress.model.sealed.DataResult
import uz.devapp.foodexpress.utils.Constants
import uz.devapp.foodexpress.utils.PrefUtils

open class BaseRepository {
    fun <T> wrapResponse(response: Response<BaseResponse<T>>): DataResult<T> {
        return if (response.isSuccessful) {
            if (response.body()?.success == true) {
                DataResult.Success(response.body()!!.data!!)
            } else {
                if (response.body()?.error_code == 1) {
                    PrefUtils.clear()
                    EventBus.getDefault().post(EventModel(Constants.EVENT_CLEAR_TOKEN, 1))
                }
                DataResult.Error(
                    response.body()?.message ?: ""
                )
            }
        } else {
            DataResult.Error(response.message())
        }
    }
}
package uz.devapp.foodexpress.screen.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.orhanobut.hawk.Hawk
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.devapp.foodexpress.api.Api
import uz.devapp.foodexpress.api.NetworkObject
import uz.devapp.foodexpress.screen.main.MainActivity
import uz.devapp.foodexpress.databinding.ActivitySignBinding
import uz.devapp.foodexpress.model.request.LoginRequest
import uz.devapp.foodexpress.model.response.BaseResponse
import uz.devapp.foodexpress.model.response.LoginResponse
import uz.devapp.foodexpress.utils.Constants.BASE_URL
import uz.devapp.foodexpress.utils.PrefUtils

class SignActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            binding.flProgress.visibility = View.VISIBLE
            NetworkObject.getClientInstance().login(LoginRequest(binding.edPhone.text.toString(), binding.edPassword.text.toString()))
                .enqueue(object: Callback<BaseResponse<LoginResponse?>>{
                    override fun onResponse(
                        call: Call<BaseResponse<LoginResponse?>>,
                        response: Response<BaseResponse<LoginResponse?>>
                    ) {
                        binding.flProgress.visibility = View.GONE
                        if(response.isSuccessful){
                            if (response.body()!!.success){
                                PrefUtils.setToken(response.body()!!.data!!.token)
                                startActivity(Intent(this@SignActivity, MainActivity::class.java))
                                finish()
                            }else{
                                Toast.makeText(this@SignActivity, response.body()!!.message, Toast.LENGTH_LONG).show()
                            }
                        }else{
                            Toast.makeText(this@SignActivity, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse<LoginResponse?>>, t: Throwable) {
                        binding.flProgress.visibility = View.GONE
                        Toast.makeText(this@SignActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                })
        }

        binding.tvRegistration.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }
    }
}
package uz.devapp.foodexpress.screen.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.hawk.Hawk
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.devapp.foodexpress.api.Api
import uz.devapp.foodexpress.api.NetworkObject
import uz.devapp.foodexpress.databinding.ActivityRegistrationBinding
import uz.devapp.foodexpress.model.request.RegistrationRequest
import uz.devapp.foodexpress.model.response.BaseResponse
import uz.devapp.foodexpress.model.response.LoginResponse
import uz.devapp.foodexpress.screen.main.MainActivity
import uz.devapp.foodexpress.utils.Constants
import uz.devapp.foodexpress.utils.PrefUtils

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistration.setOnClickListener {
            binding.flProgress.visibility = View.VISIBLE
            NetworkObject.getClientInstance().registration(
                RegistrationRequest(
                    binding.edPhone.text.toString(),
                    binding.edPassword.text.toString(),
                    binding.edFullname.text.toString()
                )
            )
                .enqueue(object : Callback<BaseResponse<LoginResponse?>> {
                    override fun onResponse(
                        call: Call<BaseResponse<LoginResponse?>>,
                        response: Response<BaseResponse<LoginResponse?>>
                    ) {
                        binding.flProgress.visibility = View.GONE
                        if (response.isSuccessful) {
                            if (response.body()!!.success) {
                                PrefUtils.setToken(response.body()!!.data!!.token)
                                startActivity(
                                    Intent(
                                        this@RegistrationActivity,
                                        MainActivity::class.java
                                    )
                                )
                                finish()
                            } else {
                                Toast.makeText(
                                    this@RegistrationActivity,
                                    response.body()!!.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this@RegistrationActivity,
                                response.errorBody()?.string(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse<LoginResponse?>>, t: Throwable) {
                        binding.flProgress.visibility = View.GONE
                        Toast.makeText(
                            this@RegistrationActivity,
                            t.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }
    }
}
package uz.devapp.foodexpress.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orhanobut.hawk.Hawk
import uz.devapp.foodexpress.databinding.ActivitySplashBinding
import uz.devapp.foodexpress.screen.auth.SignActivity
import uz.devapp.foodexpress.screen.main.MainActivity
import uz.devapp.foodexpress.utils.Constants
import uz.devapp.foodexpress.utils.PrefUtils

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgLogo.postDelayed({
            if(PrefUtils.getToken().isEmpty()){
                startActivity(Intent(this, SignActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 2000)
    }
}
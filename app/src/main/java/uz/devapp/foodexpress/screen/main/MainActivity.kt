package uz.devapp.foodexpress.screen.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import uz.devapp.foodexpress.databinding.ActivityMainBinding
import uz.devapp.foodexpress.model.EventModel
import uz.devapp.foodexpress.screen.splash.SplashActivity
import uz.devapp.foodexpress.utils.Constants

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = binding.fcvNavHost.getFragment<NavHostFragment>()
        binding.bnv.setupWithNavController(navHostFragment.navController)
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(eventModel: EventModel<Int>){
        if (eventModel.command == Constants.EVENT_CLEAR_TOKEN){
            val i = Intent(this, SplashActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }
}
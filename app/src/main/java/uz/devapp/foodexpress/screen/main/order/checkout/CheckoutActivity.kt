package uz.devapp.foodexpress.screen.main.order.checkout

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.*
import uz.devapp.foodexpress.R
import uz.devapp.foodexpress.databinding.ActivityCheckoutBinding
import uz.devapp.foodexpress.model.enum.OrderType
import uz.devapp.foodexpress.model.request.MakeOrderRequest
import uz.devapp.foodexpress.screen.main.MainActivity
import uz.devapp.foodexpress.utils.Constants
import uz.devapp.foodexpress.utils.PrefUtils
import java.util.*

class CheckoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheckoutBinding
    lateinit var viewModel: CheckoutViewModel
    var request = MakeOrderRequest(OrderType.CARD, "", 0.0, 0.0, PrefUtils.getCartList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CheckoutViewModel::class.java]

        viewModel.errorLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        viewModel.progressLiveData.observe(this) {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.successOrderLiveData.observe(this) {
            Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show()
            PrefUtils.setCartList(listOf())
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

        binding.tvTotalAmount.text = intent.getDoubleExtra(Constants.EXTRA_DATA, 0.0).toString()
        binding.lyPaymentCash.setOnClickListener {
            request.order_type = OrderType.CASH
            binding.lyPaymentCash.setBackgroundResource(R.drawable.shape_active)
            binding.imgPaymentCash.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.color_accent
                ), android.graphics.PorterDuff.Mode.SRC_IN
            )
            binding.tvPaymentCash.setTextColor(ContextCompat.getColor(this, R.color.color_accent))

            binding.lyPaymentCard.setBackgroundResource(R.drawable.shape_inactive)
            binding.imgPaymentCard.setColorFilter(
                ContextCompat.getColor(this, R.color.grey_color),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            binding.tvPaymentCard.setTextColor(ContextCompat.getColor(this, R.color.grey_text))
        }

        binding.lyPaymentCard.setOnClickListener {
            request.order_type = OrderType.CARD
            binding.lyPaymentCard.setBackgroundResource(R.drawable.shape_active)
            binding.imgPaymentCard.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.color_accent
                ), android.graphics.PorterDuff.Mode.SRC_IN
            )
            binding.tvPaymentCard.setTextColor(ContextCompat.getColor(this, R.color.color_accent))

            binding.lyPaymentCash.setBackgroundResource(R.drawable.shape_inactive)
            binding.imgPaymentCash.setColorFilter(
                ContextCompat.getColor(this, R.color.grey_color),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            binding.tvPaymentCash.setTextColor(ContextCompat.getColor(this, R.color.grey_text))
        }

        binding.btnMakeOrder.setOnClickListener {
            request.adress = binding.edAddress.text.toString()
            viewModel.makeOrder(request)
        }

        getLocation()
    }


    fun getLocation(){
        PermissionX.init(this)
            .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return@request
                    }

                    var location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if(location != null){
                        GlobalScope.launch(Dispatchers.Main) {
                            var addressText = getAddress(LatLng(location.latitude, location.longitude))

                            binding.edAddress.setText(addressText)
                        }
                        request.latitude = location.latitude
                        request.longitude = location.longitude
                    }

                } else {
                    Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show()
                }
            }
    }

    suspend fun getAddress(latLng: LatLng) = withContext(Dispatchers.IO){
        val geocoder = Geocoder(this@CheckoutActivity, Locale.getDefault())
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        addresses = withContext(Dispatchers.IO){
            geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        }

        if (addresses?.isNotEmpty() == true) {
            address = addresses[0]
            addressText = address.getAddressLine(0)
        } else{
            addressText = "its not appear"
        }
        addressText
    }


}
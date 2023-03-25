package uz.devapp.foodexpress

import android.app.Application
import com.orhanobut.hawk.Hawk

class MyApp: Application() {
    companion object{
        lateinit var app: MyApp
    }
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        app = this
    }


//    @SuppressLint("MissingPermission")
//    fun getLocation(){
//        PermissionX.init(this)
//            .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
//            .request { allGranted, grantedList, deniedList ->
//                if (allGranted) {
//                    val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
//                    val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//
//                } else {
//                    Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show()
//                }
//            }
//    }

//    private fun getAddress(latLng: LatLng): String {
//        val geocoder = Geocoder(this, Locale.getDefault())
//        val addresses: List<Address>?
//        val address: Address?
//        var addressText = ""
//
//        addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
//
//        if (addresses.isNotEmpty()) {
//            address = addresses[0]
//            addressText = address.getAddressLine(0)
//        } else{
//            addressText = "its not appear"
//        }
//        return addressText
//    }
}
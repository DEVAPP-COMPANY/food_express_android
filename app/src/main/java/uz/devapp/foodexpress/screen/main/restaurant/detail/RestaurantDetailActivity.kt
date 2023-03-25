package uz.devapp.foodexpress.screen.main.restaurant.detail

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import uz.devapp.foodexpress.adapter.CategoryAdapter
import uz.devapp.foodexpress.adapter.ProductAdapter
import uz.devapp.foodexpress.databinding.ActivityRestaurantDetailBinding
import uz.devapp.foodexpress.model.RestaurantModel
import uz.devapp.foodexpress.screen.main.restaurant.feedback.FeedbackFragment
import uz.devapp.foodexpress.utils.Constants
import uz.devapp.foodexpress.utils.loadImage

class RestaurantDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityRestaurantDetailBinding
    lateinit var viewModel: RestaurantDetailViewModel

    lateinit var item: RestaurantModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        item = if (Build.VERSION.SDK_INT < 33) {
            intent.getSerializableExtra(Constants.EXTRA_DATA) as RestaurantModel
        } else {
            intent.getSerializableExtra(Constants.EXTRA_DATA, RestaurantModel::class.java)!!
        }

        viewModel = ViewModelProvider(this)[RestaurantDetailViewModel::class.java]
        viewModel.errorLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        viewModel.progressLiveData.observe(this) {

        }

        viewModel.categoryListLiveData.observe(this) {
            binding.recyclerCategories.adapter = CategoryAdapter(it)
        }

        viewModel.foodListLiveData.observe(this) {
            binding.recyclerProducts.adapter = ProductAdapter(it)
        }

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.imgMain.loadImage(item.main_image)
        binding.tvName.text = item.name
        binding.tvAddress.text = item.address
        binding.tvPhone.text = item.phone
        binding.tvDistance.text = "%.2f".format(item.distance)
        binding.tvRating.text = item.rating.toString()
        binding.ratingBar.rating = item.rating.toFloat()

        binding.recyclerProducts.layoutManager = GridLayoutManager(this, 2)

        binding.btnFeedback.setOnClickListener {
            val fragment = FeedbackFragment.newInstance()
            fragment.show(supportFragmentManager, fragment.tag)
        }
        loadData()
    }

    fun loadData() {
        viewModel.getCategories()
        viewModel.getFoods(item.id)
    }
}
package uz.devapp.foodexpress.screen.main.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import uz.devapp.foodexpress.adapter.CategoryAdapter
import uz.devapp.foodexpress.adapter.RestaurantAdapter
import uz.devapp.foodexpress.adapter.SlideAdapter
import uz.devapp.foodexpress.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.errorLiveData.observe(this) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.progressLiveData.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.offerListLiveData.observe(this) {
            binding.recyclerSlides.adapter = SlideAdapter(it)
        }

        viewModel.categoryListLiveData.observe(this) {
            binding.recyclerCategories.adapter = CategoryAdapter(it)
        }

        viewModel.nearbyListLiveData.observe(this) {
            binding.recyclerNearbyRestourants.adapter = RestaurantAdapter(it)
        }

        viewModel.topListLiveData.observe(this) {
            binding.recyclerTopRestourants.adapter = RestaurantAdapter(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipe.setOnRefreshListener {
            loadData()
        }

        binding.recyclerSlides.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        binding.recyclerCategories.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)


        binding.recyclerNearbyRestourants.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.recyclerTopRestourants.layoutManager = GridLayoutManager(requireActivity(), 2)

        loadData()
    }

    fun loadData(){
        binding.swipe.isRefreshing = false
        viewModel.getOffers()
        viewModel.getCategories()
        viewModel.getNearbyRestaurants()
        viewModel.getTopRestaurants()
        viewModel.getUser()
    }
}
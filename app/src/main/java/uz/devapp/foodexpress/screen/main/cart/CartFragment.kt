package uz.devapp.foodexpress.screen.main.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import uz.devapp.foodexpress.adapter.CartProductAdapter
import uz.devapp.foodexpress.adapter.CartProductAdapterCallback
import uz.devapp.foodexpress.databinding.FragmentCartBinding
import uz.devapp.foodexpress.screen.main.order.checkout.CheckoutActivity
import uz.devapp.foodexpress.utils.Constants

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment(), CartProductAdapterCallback {
    lateinit var binding: FragmentCartBinding
    lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
        viewModel.errorLiveData.observe(this) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.progressLiveData.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.foodListLiveData.observe(this) {
            binding.recycler.adapter = CartProductAdapter(it, this)
            binding.lyCartAction.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
            binding.tvTotalAmount.text = it.sumOf { it.cartCount * it.price }.toString() + " UZS"
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CartFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.layoutManager = GridLayoutManager(requireActivity(), 2)

        binding.btnCheckout.setOnClickListener {
            var intent = Intent(requireActivity(), CheckoutActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA,  viewModel.foodListLiveData.value?.sumOf { it.cartCount * it.price })
            startActivity(intent)
        }

        viewModel.getFoods()
    }

    override fun onUpdate(count: Int) {
        if (count == 0) {
            viewModel.getFoods()
        } else {
            binding.tvTotalAmount.text = viewModel.foodListLiveData.value?.sumOf { it.cartCount * it.price }.toString() + " UZS"
        }
    }

}
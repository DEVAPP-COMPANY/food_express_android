package uz.devapp.foodexpress.utils

import com.orhanobut.hawk.Hawk
import uz.devapp.foodexpress.MyApp
import uz.devapp.foodexpress.model.CartModel
import uz.devapp.foodexpress.model.UserModel

object PrefUtils {
    const val PREF_TOKEN = "token"
    const val PREF_USER = "user"
    const val PREF_CART = "cart"

    fun init() {
        Hawk.init(MyApp.app).build()
    }

    fun setToken(value: String?) {
        Hawk.put(PREF_TOKEN, value)
    }

    fun getToken(): String {
        return Hawk.get(PREF_TOKEN, "")
    }

    fun setUser(value: UserModel?) {
        Hawk.put(PREF_USER, value)
    }

    fun getUser(): UserModel? {
        return Hawk.get(PREF_USER)
    }

    fun setCartList(value: List<CartModel>) {
        Hawk.put(PREF_CART, value)
    }

    fun getCartList(): List<CartModel> {
        return Hawk.get(PREF_CART, listOf())
    }

    fun add2Cart(id: Int, count: Int) {
        var items = getCartList().toMutableList()
        var cart = items.firstOrNull { it.id == id }
        if (cart != null) {
            if (count > 0) {
                cart.count = count
            } else {
                items.remove(cart)
            }
        } else {
            if (count > 0) {
                items.add(CartModel(id, count))
            }
        }
        setCartList(items)
    }

    fun getCartCount(id: Int): Int {
        return getCartList().firstOrNull { it.id == id }?.count ?: 0
    }

    fun clear(){
        Hawk.deleteAll()
    }
}
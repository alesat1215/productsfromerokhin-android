package com.alesat1215.productsfromerokhin.data

//import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
//import com.alesat1215.productsfromerokhin.RemoteDataMockAndroidTest
import com.alesat1215.productsfromerokhin.data.local.*
import com.alesat1215.productsfromerokhin.profileMockAndroidTest
import com.alesat1215.productsfromerokhin.util.RateLimiter
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject
import javax.inject.Singleton

/** For UI testing with dagger */
@Singleton
class ProductsRepositoryMock @Inject constructor() : IProductsRepository {

    override fun products() = MutableLiveData(emptyList<Product>()) //MutableLiveData(RemoteDataMockAndroidTest.productsEmptyCart)

    override fun titles() = MutableLiveData<Titles>() //MutableLiveData(RemoteDataMockAndroidTest.data.titles())

    override fun groups() = MutableLiveData<List<GroupDB>>() //MutableLiveData(RemoteDataMockAndroidTest.data.groups())

    override val productsInCart = MutableLiveData<List<Product>>(emptyList())

    override suspend fun addProductToCart(product: ProductInCart) {
        TODO("Not yet implemented")
    }

    override suspend fun delProductFromCart(product: ProductInCart) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCart() {
        TODO("Not yet implemented")
    }

    override val profile = MutableLiveData(profileMockAndroidTest())

    override suspend fun updateProfile(profile: Profile) {
        TODO("Not yet implemented")
    }

    override val remoteConfig: FirebaseRemoteConfig
        get() = TODO("Not yet implemented")
    override val limiter: RateLimiter?
        get() = TODO("Not yet implemented")
}
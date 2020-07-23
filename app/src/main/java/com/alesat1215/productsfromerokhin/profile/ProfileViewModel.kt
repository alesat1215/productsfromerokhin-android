package com.alesat1215.productsfromerokhin.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesat1215.productsfromerokhin.data.IProductsRepository
import com.alesat1215.productsfromerokhin.data.local.Profile
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val repository: IProductsRepository
): ViewModel() {
    val profile = repository.profile

    fun updateProfile(name: String, phone: String, address: String) {
        viewModelScope.launch {
            repository.updateProfile(Profile(name = name, phone = phone, address = address))
            Log.d("Profile", "Save profile: $name, $phone, $address")
        }
    }
}
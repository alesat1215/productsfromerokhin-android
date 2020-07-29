package com.alesat1215.productsfromerokhin.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alesat1215.productsfromerokhin.data.IProfileRepository
import com.alesat1215.productsfromerokhin.data.Profile
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val repository: IProfileRepository
): ViewModel() {
    val profile = repository.profile

    fun updateProfile(name: String, phone: String, address: String) {
        viewModelScope.launch {
            repository.updateProfile(
                Profile(
                    name = name,
                    phone = phone,
                    address = address
                )
            )
        }
    }
}
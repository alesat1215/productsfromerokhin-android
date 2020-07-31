package com.alesat1215.productsfromerokhin.data

import androidx.room.Entity
import androidx.room.Fts4

@Fts4
@Entity
data class PhoneForOrder(val phone: String = "")
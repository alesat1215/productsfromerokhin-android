package com.alesat1215.productsfromerokhin.data.local

import org.junit.Test

import org.junit.Assert.*

class ProfileTest {

    @Test
    fun delivery() {
        val name = "name"
        val phone = "phone"
        val address = "address"
        val separator = System.lineSeparator()
        // Empty profile
        var profile = Profile()
        assertTrue(profile.delivery().isEmpty())
        // Not empty profile
        val onlyName = separator + separator + name
        profile = Profile(name = name)
        assertEquals(profile.delivery(), onlyName)

        val onlyPhone = separator + separator + phone
        profile = Profile(phone = phone)
        assertEquals(profile.delivery(), onlyPhone)

        val onlyAddress = separator + separator + address
        profile = Profile(address = address)
        assertEquals(profile.delivery(), onlyAddress)

        val nameAddress = separator + separator + name + separator + address
        profile = Profile(name = name, address = address)
        assertEquals(profile.delivery(), nameAddress)

        val namePhone = separator + separator + name + separator + phone
        profile = Profile(name = name, phone = phone)
        assertEquals(profile.delivery(), namePhone)

        val phoneAddress = separator + separator + phone + separator + address
        profile = Profile(phone = phone, address = address)
        assertEquals(profile.delivery(), phoneAddress)
    }
}
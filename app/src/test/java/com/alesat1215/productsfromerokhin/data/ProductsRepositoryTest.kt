package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alesat1215.productsfromerokhin.data.local.ProductsDao
import com.alesat1215.productsfromerokhin.data.local.ProductsDatabase
import com.alesat1215.productsfromerokhin.util.RateLimiter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import org.junit.Test
import com.alesat1215.productsfromerokhin.remoteDataMockTest
import com.google.firebase.database.FirebaseDatabase

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductsRepositoryTest {

    @Mock
    private lateinit var authFBMock: FirebaseAuth
    @Mock
    private lateinit var dbFB: DatabaseReference
    @Mock
    private lateinit var db: ProductsDatabase
    @Mock
    private lateinit var dbFBFetchLimit: RateLimiter

    private val products: LiveData<List<Product>> = MutableLiveData(remoteDataMockTest().productsWithGroupId())
    private val groups: LiveData<List<Group>> = MutableLiveData(remoteDataMockTest().groups!!.map { it.apply { products = null } })
    private val titles: LiveData<RemoteData?> = MutableLiveData(remoteDataMockTest().apply { groups = null })

    @Before
    fun setUp() {
        `when`(authFBMock.currentUser).thenReturn(mock(FirebaseUser::class.java))
        `when`(dbFB.database).thenReturn(mock(FirebaseDatabase::class.java))
        `when`(dbFBFetchLimit.shouldFetch()).thenReturn(true)
        `when`(db.productsDao()).thenReturn(mock(ProductsDao::class.java))
        `when`(db.productsDao().products()).thenReturn(products)
        `when`(db.productsDao().groups()).thenReturn(groups)
        `when`(db.productsDao().titles()).thenReturn(titles)
    }

    @Test
    fun productsGroupsTitles() {
        val repo = ProductsRepository(authFBMock, dbFB, db, dbFBFetchLimit)
        assertEquals(repo.products(), products)
        assertEquals(repo.groups(), groups)
        assertEquals(repo.titles(), titles)
    }
}
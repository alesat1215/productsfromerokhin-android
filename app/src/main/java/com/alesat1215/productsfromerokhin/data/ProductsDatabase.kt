package com.alesat1215.productsfromerokhin.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductsDao {
    @Query("SELECT * FROM product")
    fun products(): LiveData<List<Product>>
    @Query("SELECT * FROM `group`")
    fun groups(): LiveData<List<Group>>
    @Query("SELECT * FROM remotedata LIMIT 1")
    fun titles(): LiveData<RemoteData?>
    @Query("SELECT * FROM productincart")
    fun productsInCart(): LiveData<List<ProductInCart>>

    /** Insert products, groups & titles */
    @Transaction
    fun update(data: RemoteData) {
        val products = data.productsWithGroupId()
        if (products != null)
            insertProducts(products)
        val groups = data.groups
        if (groups != null)
            insertGroups(groups)
        insertTitles(data)
        Log.d("ProductsDao", "db is updated")
    }
    @Insert(entity = Product::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<Product>)
    @Insert(entity = Group::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertGroups(products: List<Group>)
    @Insert(entity = RemoteData::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertTitles(data: RemoteData)

    /** Clear products, groups & titles */
    @Transaction
    fun clearBeforeUpdate() {
        clearProducts()
        clearGroups()
        clearTitles()
        Log.d("ProductsDao", "db is clear")
    }
    @Query("DELETE FROM product")
    fun clearProducts()
    @Query("DELETE FROM `group`")
    fun clearGroups()
    @Query("DELETE FROM remotedata")
    fun clearTitles()
}

@Database(entities = [Product::class, Group::class, RemoteData::class, ProductInCart::class], version = 1, exportSchema = false)
abstract class ProductsDatabase: RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}


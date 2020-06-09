package com.alesat1215.productsfromerokhin.data

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

    /** Insert products, groups & titles */
    @Transaction
    suspend fun update(data: RemoteData) {
        val products = data.productsWithGroupId()
        if (products != null)
            insertProducts(products)
        val groups = data.groups
        if (groups != null)
            insertGroups(groups)
        insertTitles(data)
    }
    @Insert(entity = Product::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)
    @Insert(entity = Group::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroups(products: List<Group>)
    @Insert(entity = RemoteData::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTitles(data: RemoteData)
}

@Database(entities = [Product::class, Group::class, RemoteData::class], version = 1, exportSchema = false)
abstract class ProductsDatabase: RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}


package com.alesat1215.productsfromerokhin.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.alesat1215.productsfromerokhin.data.IRemoteData

@Dao
interface ProductsDao {
    @Query(
        "SELECT productdb.name, productdb.inStart, productdb.inStart2, " +
            "productdb.consist, productdb.`group`, productdb.img, productdb.price, productincart.count " +
            "FROM productdb LEFT JOIN productincart " +
            "ON productdb.name = productincart.name AND productdb.consist = productincart.consist"
    )
    fun products(): LiveData<List<Product>>
    @Query("SELECT * FROM groupdb")
    fun groups(): LiveData<List<GroupDB>>
    @Query("SELECT * FROM titles LIMIT 1")
    fun titles(): LiveData<Titles>
    @Query("SELECT * FROM productincart")
    fun productsInCart(): LiveData<List<ProductInCart>>

    /** Insert products, groups & titles */
    @Transaction
    fun update(data: IRemoteData) {
//        val products = data.productsWithGroupId()
//        if (products != null)
//            insertProducts(products)
//        val groups = data.groups
//        if (groups != null)
//            insertGroups(groups)
//        insertTitles(data)
//        val localData = data.asLocalData()
//        insertTitles(localData.first)
//        insertGroups(localData.second)
//        insertProducts(localData.third)
        insertTitles(data.titles())
        insertGroups(data.groups())
        insertProducts(data.products())
        Log.d("ProductsDao", "db is updated")
    }
    @Insert(entity = ProductDB::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<ProductDB>)
    @Insert(entity = GroupDB::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertGroups(products: List<GroupDB>)
    @Insert(entity = Titles::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertTitles(titles: Titles)

    /** Clear products, groups & titles */
    @Transaction
    fun clearBeforeUpdate() {
        clearProducts()
        clearGroups()
        clearTitles()
        Log.d("ProductsDao", "db is clear")
    }
    @Query("DELETE FROM productdb")
    fun clearProducts()
    @Query("DELETE FROM groupdb")
    fun clearGroups()
    @Query("DELETE FROM titles")
    fun clearTitles()
}

@Database(entities = [ProductDB::class, GroupDB::class, Titles::class, ProductInCart::class], version = 1, exportSchema = false)
abstract class ProductsDatabase: RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}


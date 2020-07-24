package com.alesat1215.productsfromerokhin.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.alesat1215.productsfromerokhin.data.IRemoteData
import com.alesat1215.productsfromerokhin.data.Instruction

@Dao
interface ProductsDao {
    @Transaction
    @Query("SELECT * FROM productdb")
    fun products(): LiveData<List<Product>>
    @Query("SELECT * FROM groupdb")
    fun groups(): LiveData<List<GroupDB>>
    @Query("SELECT * FROM titles LIMIT 1")
    fun titles(): LiveData<Titles>

    /** Insert products, groups & titles */
    @Transaction
    fun update(data: IRemoteData) {
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
    @Insert(entity = ProductInCart::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertProductInCart(product: ProductInCart)
    @Delete
    fun deleteProductFromCart(product: ProductInCart)
    @Query("DELETE FROM productincart")
    fun clearCart()

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

    // Profile
    @Query("SELECT rowid, * FROM profile LIMIT 1")
    fun profile(): LiveData<Profile>
    @Insert(entity = Profile::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: Profile)
    @Query("DELETE FROM profile")
    fun clearProfile()
    @Transaction
    fun updateProfile(profile: Profile) {
        clearProfile()
        insertProfile(profile)
    }

    // Tutorial
    @Query("SELECT * FROM instruction")
    fun instructions(): LiveData<List<Instruction>>
    @Insert(entity = Instruction::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertInstructions(instructions: List<Instruction>)
    @Query("DELETE FROM instruction")
    fun clearInstructions()
}

@Database(entities = [ProductDB::class, GroupDB::class, Titles::class, ProductInCart::class, Profile::class, Instruction::class], version = 1, exportSchema = false)
abstract class ProductsDatabase: RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}


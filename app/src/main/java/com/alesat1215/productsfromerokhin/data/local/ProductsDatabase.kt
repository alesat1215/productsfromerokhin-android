package com.alesat1215.productsfromerokhin.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.alesat1215.productsfromerokhin.data.Instruction

@Dao
interface ProductsDao {



    /** Insert products, groups & titles */
//    @Transaction
//    fun update(data: IRemoteData) {
//        insertTitles(data.titles())
//        insertGroups(data.groups())
//        insertProducts(data.products())
//        Log.d("ProductsDao", "db is updated")
//    }

    // Products
    @Transaction
    @Query("SELECT * FROM productdb")
    fun products(): LiveData<List<Product>>
    @Query("SELECT * FROM groupdb")
    fun groups(): LiveData<List<GroupDB>>
    /** Clear & insert groups & products */
    @Transaction
    fun updateProducts(groups: List<GroupDB>, products: List<ProductDB>) {
        clearGroups()
        clearGroups()
        insertGroups(groups)
        insertProducts(products)
        Log.d("Database", "Update products & groups")
    }
    @Insert(entity = ProductDB::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<ProductDB>)
    @Insert(entity = GroupDB::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertGroups(products: List<GroupDB>)
    @Query("DELETE FROM productdb")
    fun clearProducts()
    @Query("DELETE FROM groupdb")
    fun clearGroups()

    // Titles
    @Query("SELECT * FROM titles LIMIT 1")
    fun titles(): LiveData<Titles>
    /** Clear & insert titles */
    @Transaction
    fun updateTitles(titles: Titles) {
        clearTitles()
        insertTitles(titles)
        Log.d("Database", "Update titles")
    }
    @Query("DELETE FROM titles")
    fun clearTitles()
    @Insert(entity = Titles::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertTitles(titles: Titles)

    // Cart
    @Insert(entity = ProductInCart::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertProductInCart(product: ProductInCart)
    @Delete
    fun deleteProductFromCart(product: ProductInCart)
    @Query("DELETE FROM productincart")
    fun clearCart()

    /** Clear products, groups & titles */
//    @Transaction
//    fun clearBeforeUpdate() {
//        clearProducts()
//        clearGroups()
//        clearTitles()
//        Log.d("ProductsDao", "db is clear")
//    }

    // Profile
    @Query("SELECT rowid, * FROM profile LIMIT 1")
    fun profile(): LiveData<Profile>
    /** Clear & insert profile */
    @Transaction
    fun updateProfile(profile: Profile) {
        clearProfile()
        insertProfile(profile)
        Log.d("Database", "Update profile")
    }
    @Insert(entity = Profile::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: Profile)
    @Query("DELETE FROM profile")
    fun clearProfile()

    // Tutorial
    @Query("SELECT * FROM instruction")
    fun instructions(): LiveData<List<Instruction>>
    /** Clear & insert instructions */
    @Transaction
    fun updateInstructions(instructions: List<Instruction>) {
        clearInstructions()
        insertInstructions(instructions)
        Log.d("Database", "Update instructions")
    }
    @Insert(entity = Instruction::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertInstructions(instructions: List<Instruction>)
    @Query("DELETE FROM instruction")
    fun clearInstructions()
}

@Database(entities = [ProductDB::class, GroupDB::class, Titles::class, ProductInCart::class, Profile::class, Instruction::class], version = 1, exportSchema = false)
abstract class ProductsDatabase: RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}


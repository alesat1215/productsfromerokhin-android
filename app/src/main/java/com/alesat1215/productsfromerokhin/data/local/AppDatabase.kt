package com.alesat1215.productsfromerokhin.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alesat1215.productsfromerokhin.data.Instruction
import com.orhanobut.logger.Logger

@Dao
interface ProductsDao {
    @Transaction
    @Query("SELECT * FROM product")
    fun products(): LiveData<List<ProductInfo>>
    @Query("SELECT * FROM `group`")
    fun groups(): LiveData<List<Group>>
    /** Clear & insert groups & products */
    @Transaction
    fun updateProducts(groups: List<Group>, products: List<Product>) {
        clearGroups()
        clearProducts()
        insertGroups(groups)
        insertProducts(products)
        Logger.d("Update products & groups")
    }
    @Insert(entity = Product::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<Product>)
    @Insert(entity = Group::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertGroups(products: List<Group>)
    @Query("DELETE FROM product")
    fun clearProducts()
    @Query("DELETE FROM `group`")
    fun clearGroups()
}

@Dao
interface TitlesDao {
    @Query("SELECT * FROM titles LIMIT 1")
    fun titles(): LiveData<Titles>
    /** Clear & insert titles */
    @Transaction
    fun updateTitles(titles: Titles) {
        clearTitles()
        insertTitles(titles)
        Logger.d("Update titles")
    }
    @Query("DELETE FROM titles")
    fun clearTitles()
    @Insert(entity = Titles::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertTitles(titles: Titles)
}

@Dao
interface CartDao {
    @Insert(entity = ProductInCart::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertProductInCart(product: ProductInCart)
    @Delete
    fun deleteProductFromCart(product: ProductInCart)
    @Query("DELETE FROM productincart")
    fun clearCart()
}

@Dao
interface ProfileDao {
    @Query("SELECT rowid, * FROM profile LIMIT 1")
    fun profile(): LiveData<Profile>
    /** Clear & insert profile */
    @Transaction
    fun updateProfile(profile: Profile) {
        clearProfile()
        insertProfile(profile)
        Logger.d("Update profile")
    }
    @Insert(entity = Profile::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: Profile)
    @Query("DELETE FROM profile")
    fun clearProfile()
}

@Dao
interface InstructionsDao {
    @Query("SELECT * FROM instruction")
    fun instructions(): LiveData<List<Instruction>>
    /** Clear & insert instructions */
    @Transaction
    fun updateInstructions(instructions: List<Instruction>) {
        clearInstructions()
        insertInstructions(instructions)
        Logger.d("Update instructions")
    }
    @Insert(entity = Instruction::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertInstructions(instructions: List<Instruction>)
    @Query("DELETE FROM instruction")
    fun clearInstructions()
}

@Database(entities = [Product::class, Group::class, Titles::class, ProductInCart::class, Profile::class, Instruction::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productsDao(): ProductsDao
    abstract fun titlesDao(): TitlesDao
    abstract fun cartDao(): CartDao
    abstract fun profileDao(): ProfileDao
    abstract fun instructionsDao(): InstructionsDao
}


package com.alesat1215.productsfromerokhin.data

import androidx.lifecycle.LiveData
import androidx.room.*
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
    suspend fun updateProducts(groups: List<Group>, products: List<Product>) {
        clearGroups()
        clearProducts()
        insertGroups(groups)
        insertProducts(products)
        Logger.d("Update products & groups")
    }
    @Insert(entity = Product::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)
    @Insert(entity = Group::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroups(products: List<Group>)
    @Query("DELETE FROM product")
    suspend fun clearProducts()
    @Query("DELETE FROM `group`")
    suspend fun clearGroups()
}

@Dao
interface TitlesDao {
    @Query("SELECT * FROM titles LIMIT 1")
    fun titles(): LiveData<Titles?>
    /** Clear & insert titles */
    @Transaction
    suspend fun updateTitles(titles: Titles) {
        clearTitles()
        insertTitles(titles)
        Logger.d("Update titles")
    }
    @Query("DELETE FROM titles")
    suspend fun clearTitles()
    @Insert(entity = Titles::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTitles(titles: Titles)
}

@Dao
interface CartDao {
    @Insert(entity = ProductInCart::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductInCart(product: ProductInCart)
    @Delete
    suspend fun deleteProductFromCart(product: ProductInCart)
    @Query("DELETE FROM productincart")
    suspend fun clearCart()
}

@Dao
interface ProfileDao {
    @Query("SELECT rowid, * FROM profile LIMIT 1")
    fun profile(): LiveData<Profile?>
    /** Clear & insert profile */
    @Transaction
    suspend fun updateProfile(profile: Profile) {
        clearProfile()
        insertProfile(profile)
        Logger.d("Update profile")
    }
    @Insert(entity = Profile::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile)
    @Query("DELETE FROM profile")
    suspend fun clearProfile()
}

@Dao
interface InstructionsDao {
    @Query("SELECT * FROM instruction")
    fun instructions(): LiveData<List<Instruction>>
    /** Clear & insert instructions */
    @Transaction
    suspend fun updateInstructions(instructions: List<Instruction>) {
        clearInstructions()
        insertInstructions(instructions)
        Logger.d("Update instructions")
    }
    @Insert(entity = Instruction::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstructions(instructions: List<Instruction>)
    @Query("DELETE FROM instruction")
    suspend fun clearInstructions()
}

@Dao
interface PhoneDao {
    @Query("SELECT * FROM phonefororder LIMIT 1")
    fun phone(): LiveData<PhoneForOrder?>
    /** Clear & insert phone */
    @Transaction
    suspend fun updatePhone(phoneForOrder: PhoneForOrder) {
        clearPhone()
        insertPhone(phoneForOrder)
        Logger.d("Update phone for order")
    }
    @Insert(entity = PhoneForOrder::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhone(phoneForOrder: PhoneForOrder)
    @Query("DELETE FROM phonefororder")
    suspend fun clearPhone()
}

@Dao
interface AboutProductsDao {
    @Query("SELECT * FROM aboutproducts")
    fun aboutProducts(): LiveData<List<AboutProducts>>
    /** Clear & insert about products */
    @Transaction
    suspend fun updateAboutProducts(aboutProducts: List<AboutProducts>) {
        clearAboutProducts()
        insertAboutProducts(aboutProducts)
        Logger.d("Update about products")
    }
    @Insert(entity = AboutProducts::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAboutProducts(aboutProducts: List<AboutProducts>)
    @Query("DELETE FROM aboutproducts")
    suspend fun clearAboutProducts()

    @Query("SELECT * FROM aboutproducts LIMIT 1")
    fun aboutProductsTitle(): LiveData<AboutProductsTitle?>
    /** Clear & insert about products title */
    @Transaction
    suspend fun updateAboutProductsTitle(aboutProductsTitle: AboutProductsTitle) {
        clearAboutProductsTitle()
        insertAboutProductsTitle(aboutProductsTitle)
        Logger.d("Update about products title")
    }
    @Insert(entity = AboutProductsTitle::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAboutProductsTitle(aboutProductsTitle: AboutProductsTitle)
    @Query("DELETE FROM aboutproductstitle")
    suspend fun clearAboutProductsTitle()
}

@Database(
    entities = [
    Product::class,
    Group::class,
    Titles::class,
    ProductInCart::class,
    Profile::class,
    Instruction::class,
    PhoneForOrder::class,
    AboutProducts::class,
    AboutProductsTitle::class
], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productsDao(): ProductsDao
    abstract fun titlesDao(): TitlesDao
    abstract fun cartDao(): CartDao
    abstract fun profileDao(): ProfileDao
    abstract fun instructionsDao(): InstructionsDao
    abstract fun phoneDao(): PhoneDao
    abstract fun aboutProductsDao(): AboutProductsDao
}


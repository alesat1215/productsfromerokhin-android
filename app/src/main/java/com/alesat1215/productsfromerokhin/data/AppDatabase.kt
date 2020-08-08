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
interface ContactsDao {
    @Query("SELECT * FROM contacts LIMIT 1")
    fun contacts(): LiveData<Contacts?>
    /** Clear & insert contacts */
    @Transaction
    suspend fun updateContacts(contacts: Contacts) {
        clearContacts()
        insertContacts(contacts)
        Logger.d("Update contacts")
    }
    @Insert(entity = Contacts::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contacts: Contacts)
    @Query("DELETE FROM contacts")
    suspend fun clearContacts()
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
}

@Dao
interface AboutAppDao {
    @Query("SELECT * FROM aboutapp LIMIT 1")
    fun aboutApp(): LiveData<AboutApp?>
    /** Clear & insert appInfo */
    @Transaction
    suspend fun updateAboutApp(aboutApp: AboutApp) {
        clearAboutApp()
        insertAboutApp(aboutApp)
        Logger.d("Update about app")
    }
    @Insert(entity = AboutApp::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAboutApp(aboutApp: AboutApp)
    @Query("DELETE FROM aboutapp")
    suspend fun clearAboutApp()
}

@Database(
    entities = [
    Product::class,
    Group::class,
    Titles::class,
    ProductInCart::class,
    Profile::class,
    Instruction::class,
    Contacts::class,
    AboutProducts::class,
    AboutApp::class
], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productsDao(): ProductsDao
    abstract fun titlesDao(): TitlesDao
    abstract fun cartDao(): CartDao
    abstract fun profileDao(): ProfileDao
    abstract fun instructionsDao(): InstructionsDao
    abstract fun contactsDao(): ContactsDao
    abstract fun aboutProductsDao(): AboutProductsDao
    abstract fun aboutApp(): AboutAppDao
}


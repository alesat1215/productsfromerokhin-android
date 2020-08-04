package com.alesat1215.productsfromerokhin.data

import com.alesat1215.productsfromerokhin.util.DatabaseUpdater
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AboutProductsRepository @Inject constructor(
    /** Room database */
    private val db: AppDatabase,
    private val dbUpdater: DatabaseUpdater,
    /** For parse JSON from remote config */
    private val gson: Gson
) {

}
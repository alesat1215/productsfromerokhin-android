package com.alesat1215.productsfromerokhin

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alesat1215.productsfromerokhin.cart.CartViewModel
import com.alesat1215.productsfromerokhin.tutorial.InstructionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.logger.Logger
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<CartViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        nav_view.setupWithNavController(findNavController(R.id.nav_host_fragment))
        setupBadge(nav_view)
    }

    private fun setupBadge(navigationView: BottomNavigationView) {
        viewModel.productsInCart.observe(this, Observer {
            val badge = navigationView.getOrCreateBadge(R.id.cartFragment)
            if (it.isEmpty()) {
                badge.isVisible = false
                badge.clearNumber()
                Logger.d("Clear cart badge")
            } else {
                badge.isVisible = true
                badge.number = it.count()
                Logger.d("Set cart badge: ${it.count()}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toollbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clearCart) {
            viewModel.clearCart()
            Logger.d("Clear cart")
            return true
        } else return super.onOptionsItemSelected(item)
    }
    // For back button in toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun setupBackButton(enabled: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
        supportActionBar?.setDisplayShowHomeEnabled(enabled)
        Logger.d("Show back button: $enabled")
    }

    /** Check tutorial read */
    fun tutorialIsRead() =
        getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            .getBoolean(IS_READ, false).also {
            Logger.d("Tutorial is read: $it")
        }

    /** Save finish status to shared prefs */
    fun finishRead() {
        // Set finish status to shared prefs
        getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            .edit().putBoolean(IS_READ, true).apply()
    }

    companion object {
        const val SHARED_PREFS = "com.alesat1215.productsfromerokhin"
        const val IS_READ = "IS_READ"
    }
}

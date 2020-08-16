package com.alesat1215.productsfromerokhin

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.alesat1215.productsfromerokhin.cart.CartViewModel
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
        val navController = findNavController(R.id.nav_host_fragment)
        // Setup AppBarConfiguration with top-level destinations
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.loadFragment,
            R.id.startFragment,
            R.id.menuFragment,
            R.id.cartFragment,
            R.id.profileFragment,
            R.id.moreFragment,
            R.id.tutorialFragment
        ))
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
        setupBadge(nav_view)
        setupNavigation(navController)
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
        return if (item.itemId == R.id.clearCart) {
            clearCart()
            true
        } else super.onOptionsItemSelected(item)
    }
    /** Create dialog for clear cart */
    private fun clearCart() {
        // Build alert
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage(R.string.clear_cart_alert)
            // Show contact card for positive button
            setPositiveButton(android.R.string.ok) { _: DialogInterface, _: Int ->
                Logger.d("Clear cart")
                viewModel.clearCart()
            }
            // Show select messenger for negative button
            setNegativeButton(android.R.string.cancel) { _: DialogInterface, _: Int ->
                Logger.d("CANCEL click")
            }
        }
        // Show alert
        builder.show()
    }

    private fun setupNavigation(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Clear toolbar title
            toolbar.title = ""
            // Toolbar & BottomNavigationView visible
            when (destination.id) {
                R.id.loadFragment, R.id.tutorialFragment -> {
                    toolbar.visibility = View.GONE
                    nav_view.visibility = View.GONE
                }
                else -> {
                    toolbar.visibility = View.VISIBLE
                    nav_view.visibility = View.VISIBLE
                }
            }
            // Clear cart button visible
            toolbar.menu.findItem(R.id.clearCart)?.isVisible = destination.id == R.id.cartFragment
        }
    }

}

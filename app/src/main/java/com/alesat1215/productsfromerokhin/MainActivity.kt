package com.alesat1215.productsfromerokhin

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
//        setupNavigation(navController)
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
            viewModel.clearCart()
            Logger.d("Clear cart")
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun setupNavigation(navController: NavController) {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            when (destination.id) {
//                R.id.startFragment,
//                R.id.menuFragment,
//                R.id.cartFragment,
//                R.id.profileFragment,
//                R.id.moreFragment -> {
//                    setupBackButton(false)
//                    controller.
//                }
//            }
        }
    }

}

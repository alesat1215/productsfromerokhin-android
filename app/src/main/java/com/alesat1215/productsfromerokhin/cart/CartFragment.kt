package com.alesat1215.productsfromerokhin.cart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.ProductInfo
import com.alesat1215.productsfromerokhin.databinding.FragmentCartBinding
import com.alesat1215.productsfromerokhin.util.BindRVAdapter
import dagger.android.support.DaggerFragment
import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * A [CartFragment] subclass of [DaggerFragment].
 * Screen with products in cart
 */
class CartFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<CartViewModel> { viewModelFactory }

    private val CONTACTS_PERMISSION_REQUEST = 0
    private val ADD_CONTACT_REQUEST = 1
    private val CHOOSER_REQUEST = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCartBinding.inflate(inflater, container, false).apply {
        // Set view model to layout
        viewModel = this@CartFragment.viewModel
        // Set fragment to layout
        fragment = this@CartFragment
        // Set adapter to products
        productsCart.adapter = adapterToProducts()
        // Set lifecycleOwner for LiveData in layout
        lifecycleOwner = this@CartFragment
        executePendingBindings()
    }.root

    /** @return adapter for products & set data to it */
    private fun adapterToProducts(): BindRVAdapter<ProductInfo> {
        val adapter = BindRVAdapter<ProductInfo>(R.layout.menu_item, viewModel)
        viewModel.products().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            Logger.d("Set list to adapter: ${it.count()}")
        })
        Logger.d("Set adapter to products_cart")
        return adapter
    }

    fun send() {
        // Check permission for contacts
        if (activity?.checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            checkContact()
            Logger.d("PERMISSION_GRANTED")
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), CONTACTS_PERMISSION_REQUEST)
                Logger.d("PERMISSION rationale true")
            } else {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), CONTACTS_PERMISSION_REQUEST)
                Logger.d("PERMISSION rationale false")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Search phone for order in contacts if permission is granted or show select messenger
        if (requestCode == CONTACTS_PERMISSION_REQUEST) {
            if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                Logger.d("PERMISSION_GRANTED")
                checkContact()
            }
            else {
                Logger.d("PERMISSION_DENIED")
                selectMessenger()
            }
        }
    }

    /** Check phone number for order in contacts */
    private fun checkContact() {
        if (!isPhoneNumberInContacts()) {
            showAlertAddContact()
        } else {
            selectMessenger()
        }
    }
    /** Show instruction for save number for order in contacts */
    private fun showAlertAddContact() {
        // Build alert
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(R.string.add_contact)
                // Show contact card for positive button
                setPositiveButton(android.R.string.ok) { dialogInterface: DialogInterface, i: Int ->
                    Logger.d("OK click")
                    addContact()
                }
                // Show select messenger for negative button
                setNegativeButton(android.R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                    Logger.d("CANCEL click")
                    selectMessenger()
                }
            }
        }
        // Show alert
        dialog?.show()
    }
    /** Show contact card whits name & number for saving */
    private fun addContact(number: String = "+79021228236") {
        // Build intent for add contact with app name & phone number for order
        val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
            type = ContactsContract.RawContacts.CONTENT_TYPE
            putExtra(ContactsContract.Intents.Insert.NAME, getString(R.string.app_name))
            putExtra(ContactsContract.Intents.Insert.PHONE, number)
            putExtra("finishActivityOnSaveCompleted", true)
        }
        // For show select messenger after result
        startActivityForResult(intent, ADD_CONTACT_REQUEST)
        Logger.d("Add contact: ${getString(R.string.app_name)}")
    }

    /** Check phone for order in contacts */
    private fun isPhoneNumberInContacts(number: String = "+79021228236"): Boolean {
        val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(number))
        val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
        // Request to contacts by phone number
        val cursor = context?.contentResolver?.query(uri, projection, null, null, null)?.also {
            if (it.moveToFirst()) {
                val contact = it.getString(0)
                Logger.d("Contact found: ${contact}")
                return true
            } else Logger.d("Contact not found")
        }
        cursor?.close()
        // Phone number for order not found in contacts
        return false
    }
    /** Show chooser for send order */
    private fun selectMessenger() {
        val order = viewModel.order()
        val total = viewModel.totalInCart()
        val delivery = viewModel.delivery()
        // Get text for order
        order.observe(viewLifecycleOwner, Observer { orderText ->
            // Unsubscribe from events
            order.removeObservers(viewLifecycleOwner)
            // Get text for total
            total.observe(viewLifecycleOwner, Observer { sum ->
                // Unsubscribe from events
                total.removeObservers(viewLifecycleOwner)
                // Get delivery info
                delivery.observe(viewLifecycleOwner, Observer {
                    // Unsubscribe from events
                    delivery.removeObservers(viewLifecycleOwner)
                    // Create text for message
                    val message = "$orderText${getString(R.string.total)} $sum ${getString(R.string.rub)}$it"
                    // Create intent
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, message)
                        type = "text/plain"
                    }
                    // Show chooser
                    val chooser: Intent = Intent.createChooser(intent, "")
                    activity?.packageManager?.also {
                        intent.resolveActivity(it)?.also {
                            startActivityForResult(chooser, CHOOSER_REQUEST)
                            Logger.d("Select messenger")
                        }
                    }
                })
            })
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Show select messenger after add phone number for order to contacts
        if (requestCode == ADD_CONTACT_REQUEST) {
            selectMessenger()
        }
        if (requestCode == CHOOSER_REQUEST) {
            clearCart()
        }
    }

    private fun clearCart() {
        // Build alert
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(R.string.clear_cart)
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
        }
        // Show alert
        dialog?.show()
    }

    override fun onStart() {
        super.onStart()
        // Clear cart button visible only for not empty cart
        viewModel.products().observe(viewLifecycleOwner, Observer { clearCartButton(it.isNotEmpty()) })
    }

    override fun onStop() {
        super.onStop()
        // Set clear cart button invisible
        clearCartButton(false)
    }

    /** Setup clear cart button visible */
    private fun clearCartButton(visible: Boolean) {
        (activity as? AppCompatActivity)?.toolbar?.menu?.findItem(R.id.clearCart)?.isVisible = visible
        Logger.d("Clear cart button visible: $visible")
    }
}

//    private fun sendViaWhatsApp() {
//        val intent = Intent().apply {
//            action = Intent.ACTION_VIEW
//            `package` = "com.whatsapp"
//            data = Uri.parse("https://api.whatsapp.com/send?phone=79021228236&text=textMessage")
//        }
//        val chooser: Intent = Intent.createChooser(intent, "title")
//        activity?.packageManager?.also {
//            intent.resolveActivity(it)?.also {
//                startActivity(chooser)
//                Log.d("Cart", "Confirm")
//            }
//        }
//    }
package com.alesat1215.productsfromerokhin.cart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alesat1215.productsfromerokhin.R
import com.alesat1215.productsfromerokhin.data.local.Product
import com.alesat1215.productsfromerokhin.databinding.FragmentCartBinding
import com.alesat1215.productsfromerokhin.util.BindRVAdapter
import dagger.android.support.DaggerFragment
import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import javax.inject.Inject

/**
 * A [CartFragment] subclass of [DaggerFragment].
 * Screen with products in cart
 */
class CartFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<CartViewModel> { viewModelFactory }

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
    private fun adapterToProducts(): BindRVAdapter<Product> {
        val adapter = BindRVAdapter<Product>(R.layout.menu_item, viewModel)
        viewModel.products().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            Log.d("Cart", "Set list to adapter: ${it.count()}")
        })
        Log.d("Cart", "Set adapter to products_cart")
        return adapter
    }

    fun confirm() {
//        val intent = Intent().apply {
//            action = Intent.ACTION_SEND
//            putExtra(Intent.EXTRA_TEXT, "textMessage")
//            type = "text/plain"
//        }


        if (activity?.checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            searchPhoneInContacts()
            addContact()
            Log.d("Cart", "PERMISSION_GRANTED")
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 0)
                Log.d("Cart", "PERMISSION rationale true")
            } else {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 0)
                Log.d("Cart", "PERMISSION rationale false")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                Log.d("Cart", "PERMISSION_GRANTED")
//                searchPhoneInContacts()
                addContact()
            }
            else Log.d("Cart", "PERMISSION_DENIED")
        }
    }

    private fun sendViaWhatsApp() {
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            `package` = "com.whatsapp"
            data = Uri.parse("https://api.whatsapp.com/send?phone=79021228236&text=textMessage")
        }
        val chooser: Intent = Intent.createChooser(intent, "title")
        activity?.packageManager?.also {
            intent.resolveActivity(it)?.also {
                startActivity(chooser)
                Log.d("Cart", "Confirm")
            }
        }
    }

    private fun addContact() {
        val contact = searchPhoneInContacts()
        if (contact == null) {
//            val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
//                type = ContactsContract.RawContacts.CONTENT_TYPE
//                putExtra(ContactsContract.Intents.Insert.NAME, getString(R.string.app_name))
//                putExtra(ContactsContract.Intents.Insert.PHONE, "+79021228236")
//                putExtra("finishActivityOnSaveCompleted", true)
//            }
//            startActivity(intent)
//            Log.d("Cart", "Add contact: ${getString(R.string.app_name)}")
            showAlertAddContact()
        } else {
            Log.d("Cart", "Found contact: ${contact}")
            selectMessenger()
        }
    }

    private fun showAlertAddContact() {
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(R.string.add_contact)
                setPositiveButton(android.R.string.ok) { dialogInterface: DialogInterface, i: Int ->
                    Log.d("Cart", "OK click")
                    showContact()
                }
                setNegativeButton(android.R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                    Log.d("Cart", "CANCEL click")
                    selectMessenger()
                }
            }
        }
        dialog?.show()
    }

    private fun showContact() {
        val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
            type = ContactsContract.RawContacts.CONTENT_TYPE
            putExtra(ContactsContract.Intents.Insert.NAME, getString(R.string.app_name))
            putExtra(ContactsContract.Intents.Insert.PHONE, "+79021228236")
            putExtra("finishActivityOnSaveCompleted", true)
        }
        startActivityForResult(intent, 1)
        Log.d("Cart", "Add contact: ${getString(R.string.app_name)}")
    }

    private fun searchPhoneInContacts(): String? {
        val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode("+79021228236"))
        val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
        context?.contentResolver?.query(uri, projection, null, null, null)?.also {
            if (it.moveToFirst()) {
                val contact = it.getString(0)
                Log.d("Cart", "Contact found: ${contact}")
                return contact
            } else Log.d("Cart", "Contact not found")
            it.close()
        }
        return null
    }

    private fun selectMessenger() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "textMessage")
            type = "text/plain"
        }

        val chooser: Intent = Intent.createChooser(intent, "")
        activity?.packageManager?.also {
            intent.resolveActivity(it)?.also {
                startActivity(chooser)
                Log.d("Cart", "Select messenger")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            selectMessenger()
        }
    }
}
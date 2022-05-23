package com.amvlabs.shopify.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.amvlabs.shopify.utils.FirebaseDatabase
import java.io.ByteArrayOutputStream


class AddItemViewModel(application: Application) : AndroidViewModel(application) {
    val context = application
    var _bitmapArray = MutableLiveData<ArrayList<Bitmap>>()
        set(value) {
            field = value
        }
    val db = FirebaseDatabase()

    fun saveData() {
        db.saveData(_bitmapArray.value!!)
    }


}
package com.amvlabs.shopify.utils

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

private const val TAG = "tag"
class FirebaseDatabase {
    val db = FirebaseFirestore.getInstance()
    var storageReference: StorageReference = FirebaseStorage.getInstance().reference
    val collectionReference = db.collection(Constant.COLLECTION_NAME)

    fun saveData(bitmaps: ArrayList<Bitmap>) {
        val uri = ArrayList<String>()
        GlobalScope.launch {
            val value:List<String> = async(Dispatchers.IO) {
                bitmaps.forEach {
                    val filePath = storageReference.child("products_image")
                        .child("img${Timestamp.now().seconds}.jpeg")
                    filePath.putBytes(convertBitmapToByteArray(it)).addOnCompleteListener {
                        filePath.downloadUrl.addOnSuccessListener {uri ->
                            Log.d("tag", "saveData: success $uri")
                        }
                    }.addOnFailureListener {
                        Log.d("tag", "fail: $it")
                    }
                    delay(1000)
                    Log.d(TAG, "saveData: hello")
                }
                uri as List<String>
            }.await()
            launch(Dispatchers.IO){
                Log.d("tag", "saveData: save$value")
            }


        }

    }

    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }
}
package com.Practice.DIACataloge

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.net.HttpURLConnection
import java.net.URL

class ItemNewActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private val characteristics = mutableMapOf<String, String>()
    private val picUrls = mutableListOf<String>()
    private val siteUrls = mutableListOf<String>()
    private val models = mutableListOf<String>()
    private lateinit var layoutImages: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_new)

        db = FirebaseFirestore.getInstance()
        layoutImages = findViewById(R.id.layoutImages)

        val titleEditText = findViewById<EditText>(R.id.etTitle)
        val descriptionEditText = findViewById<EditText>(R.id.etDescription)
        val priceEditText = findViewById<EditText>(R.id.etPrice)
        val ratingEditText = findViewById<EditText>(R.id.etRating)
        val categoryEditText = findViewById<EditText>(R.id.etCategory)
        val showRecommendedCheckBox = findViewById<CheckBox>(R.id.cbRecommended)

        setupButton(R.id.btnAddCharacteristic) {
            addCharacteristic(findViewById(R.id.etCharacteristicKey), findViewById(R.id.etCharacteristicValue))
        }
        setupButton(R.id.btnAddPicUrl) {
            addUrl(findViewById(R.id.etPicUrl), picUrls, ::addImageView, "Image URL")
        }
        setupButton(R.id.btnAddSiteUrl) {
            addUrl(findViewById(R.id.etSiteUrl), siteUrls, null, "Site URL")
        }
        setupButton(R.id.btnAddModel) {
            addUrl(findViewById(R.id.etModel), models, null, "Model")
        }

        findViewById<Button>(R.id.btnSaveItem).setOnClickListener {
            saveItem(titleEditText, descriptionEditText, priceEditText, ratingEditText, categoryEditText, showRecommendedCheckBox)
        }
    }

    private fun setupButton(buttonId: Int, action: () -> Unit) {
        findViewById<Button>(buttonId).setOnClickListener { action() }
    }

    private fun addCharacteristic(keyEditText: EditText, valueEditText: EditText) {
        val key = keyEditText.text.toString()
        val value = valueEditText.text.toString()
        if (key.isNotEmpty() && value.isNotEmpty()) {
            characteristics[key] = value
            showToast("Characteristic Added")
            keyEditText.text.clear()
            valueEditText.text.clear()
        } else {
            showToast("Enter valid characteristic")
        }
    }

    private fun addUrl(editText: EditText, urlList: MutableList<String>, imageViewFunc: ((String) -> Unit)?, urlType: String) {
        val url = editText.text.toString()
        if (url.isNotEmpty()) {
            urlList.add(url)
            showToast("$urlType Added")
            editText.text.clear()
            imageViewFunc?.invoke(url)
        } else {
            showToast("Enter valid $urlType")
        }
    }

    private fun saveItem(
        titleEditText: EditText,
        descriptionEditText: EditText,
        priceEditText: EditText,
        ratingEditText: EditText,
        categoryEditText: EditText,
        showRecommendedCheckBox: CheckBox
    ) {
        val title = titleEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val price = priceEditText.text.toString().toDoubleOrNull() ?: run {
            showToast("Invalid price")
            return
        }
        val rating = ratingEditText.text.toString().toDoubleOrNull() ?: run {
            showToast("Invalid rating")
            return
        }
        val categoryId = categoryEditText.text.toString()
        val showRecommended = showRecommendedCheckBox.isChecked

        val newItem = hashMapOf(
            "title" to title,
            "description" to description,
            "price" to price,
            "rating" to rating,
            "categoryId" to categoryId,
            "showRecommended" to showRecommended,
            "characteristics" to characteristics,
            "picUrl" to picUrls,
            "siteUrl" to siteUrls,
            "models" to models
        )

        db.collection("Items").add(newItem)
            .addOnSuccessListener {
                showToast("Item added successfully")
                finish()
            }
            .addOnFailureListener { e ->
                showToast("Failed to add item: ${e.message}")
            }
    }

    private fun addImageView(url: String) {
        val imageView = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300).apply {
                setMargins(0, 16, 0, 0)
            }
        }
        LoadImageTask(imageView).execute(url)
        layoutImages.addView(imageView)
    }

    private class LoadImageTask(private val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg urls: String): Bitmap? {
            return try {
                val connection = (URL(urls[0]).openConnection() as HttpURLConnection).apply {
                    doInput = true
                    connect()
                }
                BitmapFactory.decodeStream(connection.inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                imageView.setImageBitmap(result)
            } else {
                Toast.makeText(imageView.context, "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

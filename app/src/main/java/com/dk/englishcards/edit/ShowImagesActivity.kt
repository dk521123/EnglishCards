package com.dk.englishcards.edit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dk.englishcards.R
import com.dk.englishcards.cards.EnglishCard
import com.dk.englishcards.commons.BaseSubPageActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_show_images.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.io.File
import java.io.FileOutputStream
import java.util.regex.Pattern

class ShowImagesActivity : BaseSubPageActivity(),
    ImageListRecyclerViewAdapter.OnSelectImageListener {
    private lateinit var englishCard: EnglishCard
    private val mainHandler = Handler(Looper.getMainLooper())
    private var targetImageView: ImageView? = null
    private var targetRadioButton: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_images)

        val englishCardId = intent.getStringExtra(EnglishCard.ID_FIELD)
        val englishCard = super.dbHandler.readById(englishCardId) ?: return
        this.englishCard = englishCard
        saveImageFloatingActionButton.visibility = FloatingActionButton.INVISIBLE
        targetEnglishWordTextView.text = this.englishCard.english
        this.showImageList(false)
        doParallelTaskAsync(this, this.englishCard.english)

        saveImageFloatingActionButton.setOnClickListener {
            val imageView = this.targetImageView
            if (imageView == null) {
                Toast.makeText(
                    this, "Saving image is failed...", Toast.LENGTH_SHORT).show()
            } else {
                val bitmapDrawable = imageView.drawable as BitmapDrawable
                val image = bitmapDrawable.bitmap
                image?.let { image ->
                    applicationContext.let {
                        val imageFileName =  "${this.englishCard.english}.png"
                        val imageDirectory = it.getDir("Images", Context.MODE_PRIVATE)
                        Log.d("ShowImage", "Image Directory = $imageDirectory")
                        val imageFile = File(imageDirectory, imageFileName)
                        image.compress(
                            Bitmap.CompressFormat.PNG, 90, FileOutputStream(imageFile))
                        // To save image path into DB.
                        super.dbHandler.updateImagePath(
                            this.englishCard.englishCardId, imageFile.absolutePath)
                    }
                }
                Toast.makeText(
                    this, "Saving image is successful", Toast.LENGTH_SHORT).show()
                super.moveToMain()
            }
        }
    }

    override fun onSelectImageListener(
        selectedImageView: ImageView?, selectedRadioButton: RadioButton?) {

        val previousRadioButton = this.targetRadioButton
        this.targetImageView = selectedImageView
        this.targetRadioButton = selectedRadioButton

        if (selectedImageView == null) {
            this.saveImageFloatingActionButton.visibility = FloatingActionButton.INVISIBLE
        } else {
            this.saveImageFloatingActionButton.visibility = FloatingActionButton.VISIBLE
        }
        // To set the previous radio button off
        if (previousRadioButton != null && previousRadioButton != selectedRadioButton) {
            previousRadioButton.isChecked = false
        }
    }

    private fun showImageList(isShown: Boolean) {
        if (isShown) {
            imagesProgressbar.visibility = ProgressBar.INVISIBLE
            imagesRecyclerView.visibility = RecyclerView.VISIBLE
        } else {
            imagesProgressbar.visibility = ProgressBar.VISIBLE
            imagesRecyclerView.visibility = RecyclerView.INVISIBLE
        }
    }

    private fun doParallelTaskAsync(context: Context, englishWord: String) = GlobalScope.async {
        val task = RetrieveImagesTask()
        val imageUrlList = task.getImageUrls(englishWord)
        if (imageUrlList.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Failed to get images...",
                Toast.LENGTH_LONG
            ).show()
        } else {
            try {
                val imagesGridAdapter = ImageListRecyclerViewAdapter(imageUrlList)
                mainHandler.post(Runnable {
                    showImageList(true)
                    imagesRecyclerView.layoutManager = LinearLayoutManager(context)
                    imagesRecyclerView.adapter = imagesGridAdapter
                    imagesRecyclerView.setHasFixedSize(true)
                    imagesGridAdapter.setOnSelectImageListener(this@ShowImagesActivity)
                })
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Failed to set images adapter...",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    inner class RetrieveImagesTask {
        private val TOKEN_OF_HTTP_PROTOCAL = "http"

        fun getImageUrls(keyword: String): List<String> {
            val url = "https://www.google.com/search?as_st=y&tbm=isch&hl=ja&as_q=${keyword}" +
                    "&as_epq=&as_oq=&as_eq=&imgsz=&imgar=&imgc=&imgcolor=&imgtype=&cr=&" +
                    "as_sitesearch=&safe=images&as_filetype=&tbs=sur%3Acl"
            return try {
                val document = Jsoup.connect(url).get()
                val pattern = Pattern.compile(
                    "<img.+?src=\"${TOKEN_OF_HTTP_PROTOCAL}(.+?)\".+?>")
                val targetHtml = document.html()
                val matcher = pattern.matcher(targetHtml)

                var imageUrlList = mutableListOf<String>()
                var index = 0
                while (matcher.find()){
                    val imageUrl = TOKEN_OF_HTTP_PROTOCAL + matcher.group(1)
                    println("Image URL[$index] : $imageUrl")
                    index++
                    if (index == 1) {
                        continue
                    }
                    imageUrlList.add(imageUrl)
                }
                imageUrlList
            } catch (ex: Exception) {
                ex.printStackTrace()
                emptyList()
            }
        }
    }
}
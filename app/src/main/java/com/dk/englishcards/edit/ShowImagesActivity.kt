package com.dk.englishcards.edit

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dk.englishcards.R
import com.dk.englishcards.cards.EnglishCard
import com.dk.englishcards.commons.BaseSubPageActivity
import kotlinx.android.synthetic.main.activity_show_images.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.util.regex.Pattern

class ShowImagesActivity : BaseSubPageActivity() {
    private lateinit var englishWord: String
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_images)

        this.englishWord = intent.getStringExtra(EnglishCard.ENGLISH_FIELD)

        targetEnglishWordTextView.text = this.englishWord
        this.showImageList(false)
        doParallelTaskAsync(this, this.englishWord)
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
package com.madar.linkview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class LinkPreviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), LifecycleObserver {

    private val imageView: ImageView
    private val titleTextView: TextView
    private val descriptionTextView: TextView
    private var linkClickListener: OnLinkClickListener? = null
    private var parentConstraint: MaterialCardView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_link_preview, this, true)
        imageView = findViewById(R.id.imageView)
        titleTextView = findViewById(R.id.titleTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        parentConstraint = findViewById(R.id.parentConstraint)
        parentConstraint?.visibility = GONE

        // Set up click listener
        setOnClickListener {
            linkClickListener?.onLinkClicked()
        }

    }

    fun fetchLinkPreview(url: String) {
        this.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            val linkPreview = linkPreview(url)
            updateViews(linkPreview)
        }
    }

    private suspend fun updateViews(linkPreview: LinkPreview?) = withContext(Dispatchers.Main){
        runCatching {
            parentConstraint?.visibility = VISIBLE
            titleTextView.visibility = if (linkPreview?.title.isNullOrEmpty()) GONE else VISIBLE
            descriptionTextView.visibility =
                if (linkPreview?.description.isNullOrEmpty()) GONE else VISIBLE
            titleTextView.text = linkPreview?.title
            descriptionTextView.text = linkPreview?.description
            Glide.with(imageView.context)
                .load(linkPreview?.imageUrl)
                .into(imageView)
        }

    }

    fun setOnLinkClickListener(listener: OnLinkClickListener) {
        this.linkClickListener = listener
    }

    fun bindLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }


    interface OnLinkClickListener {
        fun onLinkClicked()
    }

    private suspend fun linkPreview(url: String): LinkPreview? = withContext(Dispatchers.IO) {
        return@withContext try {
            val doc = Jsoup.connect(url).get()
            val title = doc.title()
            val description = doc.select("meta[name=description]").attr("content")
            val imageUrl = doc.select("meta[property=og:image]").attr("content")
            val linkPreview = LinkPreview(title, description, imageUrl)
            linkPreview

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }


}

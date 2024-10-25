package com.dicoding.fundamentalsub1.ui.detail

import ApiConfig
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.dicoding.fundamentalsub1.data.response.DetailResponse
import com.dicoding.fundamentalsub1.data.response.Event
import com.dicoding.fundamentalsub1.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var eventLink: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, -1)

        if (eventId != -1) {
            fetchEventDetails(eventId)
        } else {
            showError("Event ID tidak valid")
        }

        binding.btnOpenLink.setOnClickListener {
            openEventLink()
        }
    }

    private fun fetchEventDetails(eventId: Int) {
        showLoading(true)

        val client = ApiConfig.getApiService().getDetailEvent(eventId.toString())
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val event = response.body()?.event
                    bindEventDetails(event)
                } else {
                    showError("Gagal memuat detail event: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                showLoading(false)
                showError("Error: ${t.message}")
            }
        })
    }

    private fun bindEventDetails(event: Event?) {
        event?.let {
            binding.tvEventName.text = it.name
            binding.tvEventDescription.text = HtmlCompat.fromHtml(it.description ?: "Deskripsi tidak tersedia", HtmlCompat.FROM_HTML_MODE_LEGACY)
            Glide.with(this)
                .load(it.mediaCover)
                .into(binding.ivEventLogo)

            binding.tvEventOwner.text = it.ownerName
            binding.tvEventTime.text = it.beginTime
            val remainingQuota = (it.quota ?: 0) - (it.registrants ?: 0)
            binding.tvEventQuota.text = "Sisa Kuota: $remainingQuota"
            eventLink = it.link
        }
    }

    private fun openEventLink() {
        if (!eventLink.isNullOrEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(eventLink))
            startActivity(intent)
        } else {
            showError("Link tidak tersedia")
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
    }
}

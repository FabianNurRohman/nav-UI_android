package com.dicoding.fundamentalsub1.ui.home

import ApiConfig
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.fundamentalsub1.data.response.EventResponse
import com.dicoding.fundamentalsub1.databinding.FragmentHomeBinding
import com.dicoding.fundamentalsub1.ui.EventAdapter
import com.dicoding.fundamentalsub1.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()

        fetchHomeEvents()
    }

    private fun setupRecyclerViews() {
        binding.rvFinishedEvents.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvUpcomingEvents.layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false )
    }

    private fun fetchHomeEvents() {
        showLoading(true)

        val clientFinished = ApiConfig.getApiService().getFinishedEvents()
        clientFinished.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val finishedEvents = response.body()?.listEvents ?: emptyList()
                    val adapterFinished = EventAdapter(events = finishedEvents) { event ->
                        onEventClick(event.id)
                    }
                    binding.rvFinishedEvents.adapter = adapterFinished
                } else {
                    showError("Gagal memuat event: ${response.message()}")
                }
                showLoading(false)
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                showError("Error: ${t.message}")
                showLoading(false)
            }
        })

        val clientUpcoming = ApiConfig.getApiService().getActiveEvents()
        clientUpcoming.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val upcomingEvents = response.body()?.listEvents ?: emptyList()
                    val adapterUpcoming = EventAdapter(events = upcomingEvents) { event ->
                        onEventClick(event.id)
                    }
                    binding.rvUpcomingEvents.adapter = adapterUpcoming
                } else {
                    showError("Gagal memuat event: ${response.message()}")
                }
                showLoading(false)
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                showError("Error: ${t.message}")
                showLoading(false)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun onEventClick(eventId: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_EVENT_ID, eventId)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

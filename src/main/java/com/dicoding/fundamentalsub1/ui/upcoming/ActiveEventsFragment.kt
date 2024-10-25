package com.dicoding.fundamentalsub1.ui.upcoming

import ApiConfig
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.fundamentalsub1.R
import com.dicoding.fundamentalsub1.data.response.EventResponse
import com.dicoding.fundamentalsub1.databinding.FragmentActiveEventsBinding
import com.dicoding.fundamentalsub1.ui.EventAdapter
import com.dicoding.fundamentalsub1.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActiveEventsFragment : Fragment() {

    private var _binding: FragmentActiveEventsBinding? = null
    private val binding get() = _binding!!

    private lateinit var activeEventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActiveEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvActiveEvents.layoutManager = LinearLayoutManager(requireContext())

        activeEventAdapter = EventAdapter(listOf()) { event ->
            onEventClick(event.id)
        }
        binding.rvActiveEvents.adapter = activeEventAdapter

        fetchActiveEvents()
    }

    private fun fetchActiveEvents() {
        showLoading(true)

        val client = ApiConfig.getApiService().getActiveEvents()
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                showLoading(false)

                if (response.isSuccessful) {
                    val activeEvents = response.body()?.listEvents ?: emptyList()
                    activeEventAdapter.setEvents(activeEvents)
                } else {
                    Toast.makeText(context, getString(R.string.failed_load_data), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onEventClick(eventId: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_EVENT_ID, eventId)
        }
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

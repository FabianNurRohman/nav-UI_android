package com.dicoding.fundamentalsub1.ui.finished

import ApiConfig
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.fundamentalsub1.data.response.EventResponse
import com.dicoding.fundamentalsub1.data.response.ListEventsItem
import com.dicoding.fundamentalsub1.databinding.FragmentFinishedEventsBinding
import com.dicoding.fundamentalsub1.ui.EventAdapter
import com.dicoding.fundamentalsub1.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedEventsFragment : Fragment() {

    private var _binding: FragmentFinishedEventsBinding? = null
    private val binding get() = _binding!!

    private lateinit var finishedEventAdapter: EventAdapter
    private lateinit var searchView: SearchView

    private var searchHandler: Handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    private var allEvents: List<ListEventsItem> = listOf()
    private var currentQuery: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFinishedEvents.layoutManager = GridLayoutManager(requireContext(), 2)
        finishedEventAdapter = EventAdapter(listOf()) { event ->
            onEventClick(event.id)
        }
        binding.rvFinishedEvents.adapter = finishedEventAdapter

        fetchFinishedEvents()

        setupSearchView()

        savedInstanceState?.getString(SEARCH_QUERY_KEY)?.let { query ->
            currentQuery = query
            filterEvents(query)
            searchView.setQuery(query, false)
        }
    }

    private fun setupSearchView() {
        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                currentQuery = query
                filterEvents(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchRunnable?.let { searchHandler.removeCallbacks(it) }
                searchRunnable = Runnable {
                    currentQuery = newText
                    filterEvents(newText)
                }
                searchHandler.postDelayed(searchRunnable!!, 500)

                return true
            }
        })
    }

    private fun filterEvents(query: String?) {
        if (query.isNullOrEmpty()) {
            finishedEventAdapter.setEvents(allEvents)
        } else {
            val filteredEvents = allEvents.filter { event ->
                event.name.contains(query, ignoreCase = true)
            }
            finishedEventAdapter.setEvents(filteredEvents)
        }
    }

    private fun fetchFinishedEvents() {
        showLoading(true)

        val client = ApiConfig.getApiService().getFinishedEvents()
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                showLoading(false)

                if (response.isSuccessful) {
                    val finishedEvents = response.body()?.listEvents ?: emptyList()
                    allEvents = finishedEvents
                    finishedEventAdapter.setEvents(finishedEvents)
                } else {
                    Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_QUERY_KEY, currentQuery)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SEARCH_QUERY_KEY = "search_query"
    }
}
package com.dicoding.fundamentalsub1.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.fundamentalsub1.R
import com.dicoding.fundamentalsub1.data.response.ListEventsItem

class EventAdapter(
    private var events: List<ListEventsItem> = listOf(),
    private val onClick: (ListEventsItem) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var filteredEvents: List<ListEventsItem> = events

    fun setEvents(events: List<ListEventsItem>) {
        this.events = events
        this.filteredEvents = events
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        val filteredEvents = events.filter { event ->
            event.name.contains(query, ignoreCase = true) ||
                    event.summary.contains(query, ignoreCase = true)
        }
        setEvents(filteredEvents)
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageEvent: ImageView = itemView.findViewById(R.id.imageEvent)
        private val tvEventName: TextView = itemView.findViewById(R.id.tvEventName)

        fun bind(event: ListEventsItem) {
            tvEventName.text = event.name

            Glide.with(itemView.context)
                .load(event.mediaCover)
                .into(imageEvent)

            itemView.setOnClickListener {
                onClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(filteredEvents[position])
    }

    override fun getItemCount(): Int = filteredEvents.size
}

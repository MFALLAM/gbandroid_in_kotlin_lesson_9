package com.example.gblesson4.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gblesson4.R
import com.example.gblesson4.model.Weather

class HistoryFragmentAdapter(
    private var onItemViewClickListener: HistoryListFragment.OnItemViewClickListener?
) :
    RecyclerView.Adapter<HistoryFragmentAdapter.HistoryViewHolder>() {

    private var weatherData: List<Weather> = listOf()

    fun setWeather(data: List<Weather>) {

        val historyDiffUtil = HistoryListDiffUtilCallback(weatherData, data)
        val result = DiffUtil.calculateDiff(historyDiffUtil)

        weatherData = data
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_history_li`st_recycler_item, parent, false)
                as View
        ) }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int { return weatherData.size }

    fun removeListener() {
        onItemViewClickListener = null
    }

    inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) {
            itemView.apply {
                findViewById<AppCompatTextView>(R.id.historyCityRecycleViewItem).text = weather.city.name
                findViewById<AppCompatTextView>(R.id.historyTempRecycleViewItem).text = weather.temperature.toString()
                setOnClickListener { onItemViewClickListener?.onItemViewClick(weather) }
            }
        }
    }
}
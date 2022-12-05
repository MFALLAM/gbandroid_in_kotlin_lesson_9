package com.example.gblesson4.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gblesson4.R
import com.example.gblesson4.model.Weather
import com.google.android.material.snackbar.Snackbar

class HistoryListFragment : Fragment() {

    private var _binding: FragmentHistoryListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherHistoryModelFromRoom by lazy {
        ViewModelProvider(this)[WeatherHistoryModelFromRoom::class.java]
    }

    private val adapter = HistoryFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            activity?.run {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container, HistoryFragmentDetails.newInstance(
                        Bundle().apply {
                            putParcelable(HistoryFragmentDetails.BUNDLE_EXTRA, weather)
                        }
                    ))
                    .addToBackStack("")
                    .commit()
            }
        }
    })

    companion object {
        fun newInstance() = HistoryListFragment()
    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.historyFragmentRecyclerView.adapter = adapter

        viewModel.getLiveData().observe(viewLifecycleOwner) { appState -> renderData(appState) }
        viewModel.getAllWeather()
    }

    private fun renderData(appState: AppStateRoom) = when (appState) {
        is AppStateRoom.Success -> {
            adapter.setWeather(appState.weather)
        }
        is AppStateRoom.Error -> {

        }
        else -> {}
    }.also {
        if (appState == AppStateRoom.Loading) binding.historyFragmentLoadingLayout.visibility =
            View.VISIBLE
        else binding.historyFragmentLoadingLayout.visibility = View.GONE
    }


    override fun onDestroy() {
        _binding = null
        adapter.removeListener()
        super.onDestroy()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }
}
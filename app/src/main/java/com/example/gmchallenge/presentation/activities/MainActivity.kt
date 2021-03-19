package com.example.gmchallenge.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gmchallenge.R
import com.example.gmchallenge.app.utils.hideKeyboardOnDrag
import com.example.gmchallenge.data.models.ArtistDataState
import com.example.gmchallenge.databinding.ActivityMainBinding
import com.example.gmchallenge.presentation.features.ArtistViewModel
import com.example.gmchallenge.presentation.features.TrackAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MainActivity : AppCompatActivity(), HasAndroidInjector {

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    internal lateinit var artistViewModel: ArtistViewModel

    private lateinit var binding: ActivityMainBinding
    private val trackAdapter = TrackAdapter()
    private var trackWatcher: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.app_bar_title)

        binding.artistInput.setEndIconOnClickListener {
            binding.artistInput.editText?.text?.clear()
            artistViewModel.resetArtistData()
        }

        binding.searchArtistButton.setOnClickListener {
            handleSearchClick()
        }

        binding.tracks.adapter = trackAdapter
        binding.tracks.layoutManager = LinearLayoutManager(this)
        binding.tracks.hideKeyboardOnDrag()
        trackAdapter.submitList(emptyList())
    }

    override fun onStart() {
        super.onStart()
        trackWatcher?.cancel()
        trackWatcher = lifecycleScope.launch {
            artistViewModel.artistData.collect { artistDataState ->
                updateUi(artistDataState)
            }
        }
    }

    override fun onStop() {
        trackWatcher?.cancel()
        super.onStop()
    }

    private fun handleSearchClick() {
        val artistName = binding.artistInput.editText?.text.toString().trim()

        if (artistName.isNotEmpty()) {
            artistViewModel.loadArtistData(artistName)
        }
    }

    private fun updateUi(artistDataState: ArtistDataState) {
        when (artistDataState) {
            is ArtistDataState.Loading -> binding.loadingSpinner.isVisible = true
            is ArtistDataState.Success -> {
                binding.loadingSpinner.isVisible = false
                trackAdapter.submitList(artistDataState.data)
            }
            is ArtistDataState.Error -> {
                binding.loadingSpinner.isVisible = false
                Snackbar.make(
                    binding.root,
                    "Error: ${artistDataState.exception.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}
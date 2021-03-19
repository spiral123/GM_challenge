package com.example.gmchallenge.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gmchallenge.R
import com.example.gmchallenge.databinding.ActivityMainBinding
import com.example.gmchallenge.presentation.features.ArtistViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MainActivity : AppCompatActivity(), HasAndroidInjector {

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    internal lateinit var artistViewModel: ArtistViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.app_bar_title)

        binding.artistInput.setEndIconOnClickListener {
            supportActionBar?.title = getString(R.string.app_bar_title)
            binding.artistInput.editText?.text?.clear()
        }

        binding.searchArtistButton.setOnClickListener {
            handleSearchClick()
        }
    }

    private fun handleSearchClick() {
        val artistName = binding.artistInput.editText?.text.toString().trim()

        if (artistName.isNotEmpty()) {
            artistViewModel.loadArtistData(artistName)
        }
    }
}
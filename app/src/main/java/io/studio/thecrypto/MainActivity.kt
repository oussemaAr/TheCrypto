package io.studio.thecrypto

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.studio.thecrypto.databinding.ActivityMainBinding
import io.studio.thecrypto.presentation.MainViewModel
import io.studio.thecrypto.presentation.UiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeData()
        binding.fab.setOnClickListener {
            viewModel.fetchRate()
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .uiState
                    .collectLatest { uiState ->
                        when (uiState) {
                            UiState.Error -> {
                                binding.loader.isVisible = false
                                Snackbar.make(
                                    binding.root,
                                    getString(R.string.loading_rate_error_message),
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            UiState.Loading -> {
                                binding.loader.isVisible = true
                            }
                            is UiState.Success -> {
                                binding.loader.isVisible = false
                                binding.rate.text = uiState.rate.rate.toString()
                            }
                        }
                    }
            }
        }
    }
}
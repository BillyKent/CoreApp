package me.doapps.core.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_suggestions.*
import kotlinx.android.synthetic.main.toolbar_back.*
import me.doapps.core.R
import me.doapps.core.databinding.ActivitySuggestionsBinding
import me.doapps.core.extension.setToolbarWithBackButton
import me.doapps.core.extension.showLongToast
import me.doapps.core.viewmodel.SuggestionsViewModel

class SuggestionsActivity : AppCompatActivity() {

    lateinit var suggestionsViewModel: SuggestionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        suggestionsViewModel = ViewModelProviders.of(this).get(SuggestionsViewModel::class.java)
        val binding: ActivitySuggestionsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_suggestions)
        binding.suggestionsViewModel = suggestionsViewModel
        binding.inputText = inputEditTextSuggestion

        setToolbarWithBackButton("Sugerencias", toolbar)
        observeViewModel()
    }

    private fun observeViewModel() {

        suggestionsViewModel.toastMessage.observe(this, Observer {
            showLongToast(message = it)
        })

        suggestionsViewModel.sendSuccess.observe(this, Observer {
            finish()
        })

    }
}

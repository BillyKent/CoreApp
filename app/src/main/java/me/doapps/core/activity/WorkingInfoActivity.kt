package me.doapps.core.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_working_info.*
import kotlinx.android.synthetic.main.toolbar_back.*
import me.doapps.core.R
import me.doapps.core.adapter.DocumentListAdapter
import me.doapps.core.extension.setToolbarWithBackButton
import me.doapps.core.extension.showLongToast
import me.doapps.core.session.Preferences
import me.doapps.core.viewmodel.WorkingInfoViewModel

class WorkingInfoActivity : AppCompatActivity() {

    lateinit var viewmodel: WorkingInfoViewModel
    lateinit var adapter: DocumentListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = ViewModelProviders.of(this).get(WorkingInfoViewModel::class.java)
        viewmodel.preferences = Preferences.getInstance(this)
        setContentView(R.layout.activity_working_info)

        setToolbarWithBackButton("Laboral", toolbar)

        val html = getString(R.string.working_info_text)
        workInfoDescriptionTextView.text =
            HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)

        observeViewModel()

    }

    fun observeViewModel() {
        viewmodel.loadDocuments()
        viewmodel.toastMessage.observe(this, Observer {
            showLongToast(message = it)
        })

        viewmodel.documentList.observe(this, Observer {
            if (adapter == null) {
                adapter = DocumentListAdapter()
            }
            adapter.documentList = it
            adapter.notifyDataSetChanged()
        })
    }
}

package me.doapps.core.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_assistance.*
import me.doapps.core.R
import me.doapps.core.utils.showShortToast
import me.doapps.core.session.Preferences
import me.doapps.core.viewmodel.AssistanceViewModel

class AssistanceActivity : AppCompatActivity() {

    lateinit var viewmodel: AssistanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assistance)
        viewmodel = ViewModelProviders.of(this).get(AssistanceViewModel::class.java)
        viewmodel.preferences = Preferences.getInstance(this)

        scannerView.setResultHandler(viewmodel)
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        scannerView.let {
            it.setResultHandler(viewmodel)
            it.startCamera()
        }
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    fun observeViewModel() {
        viewmodel.toastMessage.observe(this, Observer {
            showShortToast(message = it)
        })

        viewmodel.assistanceSuccess.observe(this, Observer {
            if (it) {
                finish()
            } else {
                Handler().postDelayed(Runnable { scannerView.resumeCameraPreview(viewmodel) }, 2000)
            }
        })


    }

}

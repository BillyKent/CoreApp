package me.doapps.core.activity

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_home.*
import me.doapps.core.R
import me.doapps.core.adapter.HomeListAdapter
import me.doapps.core.databinding.ActivityHomeBinding
import me.doapps.core.session.Preferences
import me.doapps.core.utils.*
import me.doapps.core.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var viewmodel: HomeViewModel

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {

            Constants.requestCameraPermissionCode -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openActivity(AssistanceActivity::class.java)
                }
            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewmodel.preferences = Preferences.getInstance(this)
        viewmodel.signInClient = getGoogleSignInClientFromContext()!!

        // layout binding configuration
        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewmodel = viewmodel
        binding.executePendingBindings()

        // recyclerview configuration
        mRecyclerView.adapter = HomeListAdapter(viewmodel)
        mRecyclerView.setHasFixedSize(true)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewmodel.destiny.observe(this, Observer {
            when (it) {
                HomeViewModel.Destination.DEV_TOOLS -> openActivity(BenefitsActivity::class.java) {
                    putInt(Constants.benefitTypeExtraName, Constants.toolsBenefits)
                    putString(Constants.benefitNameExtraName, "Herramientas")
                }

                HomeViewModel.Destination.ENTERTAINMENT -> openActivity(BenefitsActivity::class.java) {
                    putInt(Constants.benefitTypeExtraName, Constants.entertainmentBenefits)
                    putString(Constants.benefitNameExtraName, "Entretenimiento")
                }

                HomeViewModel.Destination.WORKING_INFO -> openActivity(WorkingInfoActivity::class.java)
                HomeViewModel.Destination.SUGGESTIONS -> openActivity(SuggestionsActivity::class.java)
                HomeViewModel.Destination.ASSISTANCE -> {

                    if (checkPermission(android.Manifest.permission.CAMERA)) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.CAMERA),
                            Constants.requestCameraPermissionCode
                        )
                    } else {
                        openActivity(AssistanceActivity::class.java)
                    }


                }

                HomeViewModel.Destination.LOGOUT -> {
                    openAndFinishActivity(LoginActivity::class.java)
                }
            }
        })
    }

}

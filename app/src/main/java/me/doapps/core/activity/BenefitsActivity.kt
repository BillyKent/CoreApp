package me.doapps.core.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_benefits.*
import kotlinx.android.synthetic.main.toolbar_back.*
import me.doapps.core.R
import me.doapps.core.adapter.BenefitsListAdapter
import me.doapps.core.utils.setToolbarWithBackButton
import me.doapps.core.utils.showLongToast
import me.doapps.core.session.Preferences
import me.doapps.core.utils.Constants
import me.doapps.core.viewmodel.BenefitsViewModel

class BenefitsActivity : AppCompatActivity() {

    private lateinit var mAdapter: BenefitsListAdapter
    private lateinit var benefitsViewModel: BenefitsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        benefitsViewModel = ViewModelProviders.of(this).get(BenefitsViewModel::class.java)
        benefitsViewModel.preferences = Preferences.getInstance(this)

        setContentView(R.layout.activity_benefits)


        setToolbarWithBackButton(intent.getStringExtra(Constants.benefitNameExtraName), toolbar)
        mAdapter = BenefitsListAdapter(benefitsRecyclerView)

        benefitsRecyclerView.adapter = mAdapter
        benefitsRecyclerView.setHasFixedSize(true)

        observeViewModel()
    }

    fun observeViewModel() {

        benefitsViewModel.loadBenefits(
            intent.getIntExtra(
                Constants.benefitTypeExtraName,
                Constants.entertainmentBenefits
            )
        )

        benefitsViewModel.benefits.observe(this, Observer {
            if (it.isEmpty()) {
                showLongToast(message = "No hay beneficios")
            } else {
                mAdapter.benefitList = it
                mAdapter.notifyDataSetChanged()

            }
        })
    }

}

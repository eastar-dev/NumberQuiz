package dev.eastar.main

import android.log.LogActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import dev.eastar.presentation.R

@AndroidEntryPoint
class Main : LogActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit { replace(R.id.container, MainFr.newInstance()) }
        }

        viewModel.exit.observe(this) {
            finish()
        }

        viewModel.moveSingleGame.observe(this) {
            supportFragmentManager.commit {
                replace(R.id.container, SingleFr::class.java.newInstance())
                addToBackStack(null)
            }
        }
        viewModel.moveMultiGame.observe(this) {
            supportFragmentManager.commit {
                replace(R.id.container, MultiFr::class.java.newInstance())
                addToBackStack(null)
            }
        }
    }
}
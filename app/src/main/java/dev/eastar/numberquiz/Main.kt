package dev.eastar.numberquiz

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import dev.eastar.numberquiz.main.MainFr
import dev.eastar.numberquiz.main.MainViewModel

@AndroidEntryPoint
class Main : AppCompatActivity() {

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

        viewModel.moveFragment.observe(this) { clz ->
            android.log.Log.e(clz)
            supportFragmentManager.commit { replace(R.id.container, clz.newInstance()) }
        }
    }
}
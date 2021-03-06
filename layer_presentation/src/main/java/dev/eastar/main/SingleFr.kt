package dev.eastar.main

import android.log.Log
import android.log.LogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.eastar.ktx.alert
import dev.eastar.ktx.hideKeyboard
import dev.eastar.ktx.positiveButton
import dev.eastar.presentation.databinding.SingleFrBinding

@AndroidEntryPoint
class SingleFr : LogFragment() {
    private lateinit var bb: SingleFrBinding
    private val viewModel: SingleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bb = SingleFrBinding.inflate(inflater, container, false)
        bb.viewmodel = viewModel
        bb.lifecycleOwner = this
        return bb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onLoadOnce()
    }

    private fun onLoadOnce() {
        viewModel.gameEnd.observe(viewLifecycleOwner) {
            hideKeyboard()
            alert(it) {
                positiveButton("OK")
                setOnDismissListener { parentFragmentManager.popBackStack() }
            }
        }

        bb.tryingNumber.setOnEditorActionListener { v, actionId, event ->
            Log.e(v, actionId, event)
            viewModel.tryNumber()
            bb.tryingNumber.setSelection(0, bb.tryingNumber.text.toString().length)
            true
        }
    }
}
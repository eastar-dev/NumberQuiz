package dev.eastar.numberquiz.main

import android.log.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.eastar.ktx.alert
import dev.eastar.ktx.positiveButton
import dev.eastar.numberquiz.databinding.SingleFrBinding

@AndroidEntryPoint
class SingleFr : Fragment() {
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
//        viewModel.signumTest(1)
        onLoadOnce()
    }

    private fun onLoadOnce() {
        viewModel.gameEnd.observe(viewLifecycleOwner) {
            alert(it) { positiveButton("OK") }
        }

        bb.tryingNumber.setOnEditorActionListener { v, actionId, event ->
            Log.e(v, actionId, event)
            viewModel.tryNumber()
            bb.tryingNumber.setSelection(0,bb.tryingNumber.text.toString().length)
            true
        }
    }
}
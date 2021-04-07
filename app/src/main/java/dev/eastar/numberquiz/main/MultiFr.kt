package dev.eastar.numberquiz.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.eastar.numberquiz.databinding.MultiFrBinding
import dev.eastar.numberquiz.databinding.SingleFrBinding

@AndroidEntryPoint
class MultiFr : Fragment() {
    private lateinit var bb: MultiFrBinding
    private val viewModel: MultiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bb = MultiFrBinding.inflate(inflater, container, false)
        bb.viewmodel = viewModel
        bb.lifecycleOwner = this
        return bb.root
    }
}
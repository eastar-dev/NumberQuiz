package dev.eastar.numberquiz.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.eastar.numberquiz.R
import dev.eastar.numberquiz.databinding.MultiFrBinding

class MultiFr : Fragment() {

    companion object {
        fun newInstance() = MultiFr()
    }

    private lateinit var bb: MultiFrBinding
    private val viewModel: MultiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bb = MultiFrBinding.inflate(inflater, container, false)
        bb.viewmodel = viewModel
        return inflater.inflate(R.layout.multi_fr, container, false)
    }
}
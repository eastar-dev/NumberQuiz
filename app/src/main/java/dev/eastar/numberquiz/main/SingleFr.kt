package dev.eastar.numberquiz.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.eastar.numberquiz.databinding.SingleFrBinding

@AndroidEntryPoint
class SingleFr : Fragment() {

    companion object {
        fun newInstance() = SingleFr()
    }

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
    }
}
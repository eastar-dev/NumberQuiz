package dev.eastar.main

import android.log.LogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dev.eastar.presentation.databinding.MainFrBinding

class MainFr : LogFragment() {

    companion object {
        fun newInstance() = MainFr()
    }

    private lateinit var bb: MainFrBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bb = MainFrBinding.inflate(inflater, container, false)
        bb.viewmodel = viewModel
        return bb.root
    }

}
package dev.eastar.numberquiz.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dev.eastar.numberquiz.databinding.MainFrBinding

class MainFr : Fragment() {

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
package dev.eastar.numberquiz.main

import android.log.Log
import android.log.LogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.eastar.ktx.alert
import dev.eastar.ktx.hideKeyboard
import dev.eastar.ktx.negativeButton
import dev.eastar.ktx.positiveButton
import dev.eastar.numberquiz.databinding.MultiFrBinding

@AndroidEntryPoint
class MultiFr : LogFragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.signumTest(1)
        onLoadOnce()
    }

    private fun onLoadOnce() {
        viewModel.memberInput.observe(viewLifecycleOwner) {
            alert("참가자를 입력해주세요") {
                val edit = EditText(context)
                setView(edit)
                positiveButton("OK") {
                    viewModel.setMembers(edit.text.toString())
                }
                negativeButton("CANCEL") {
                    parentFragmentManager.popBackStack()
                }
            }
        }
        viewModel.members1Player.observe(viewLifecycleOwner) {
            alert(it) {
                positiveButton("OK")
            }
        }
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
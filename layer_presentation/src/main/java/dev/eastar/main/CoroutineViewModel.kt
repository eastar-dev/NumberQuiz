package dev.eastar.main

import android.log.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutineViewModel : ViewModel() {
    val members = MutableLiveData<Array<String>>()

    fun setMembersAsync(membersText: String) = viewModelScope.launch {
        Log.w("\t\tstart - setMembersAsync")
        Log.w("\t\tdelay start - setMembersAsync delay(1)")
        delay(1000)
        Log.w("\t\tdelay end - setMembersAsync delay(1)")
        val playerArray = membersText.split(",").filter { it.isNotBlank() }.toTypedArray()
        members.value = playerArray
        Log.w("\t\tend - setMembersAsync")
        //useCase.doSomeThing()
    }
}
/*

start - setUp
	start - setMembersAsyncTest
	start - viewModel.setMembersAsync("성춘향")
		start - setMembersAsync
		delay start - setMembersAsync delay(1)
	end - viewModel.setMembersAsync("성춘향")
	start - advanceUntilIdle
		delay end - setMembersAsync delay(1)
		end - setMembersAsync
	end - advanceUntilIdle
	end - setMembersAsyncTest
start - tearDown


start - setUp
	start - setMembersAsyncTest
	start - viewModel.setMembersAsync("성춘향")
		start - setMembersAsync
		delay start - setMembersAsync delay(1)
	end - viewModel.setMembersAsync("성춘향")
	nothing - advanceUntilIdle
		delay end - setMembersAsync delay(1)
		end - setMembersAsync
start - tearDown

*/
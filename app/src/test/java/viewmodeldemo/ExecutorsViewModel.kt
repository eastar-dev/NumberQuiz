package viewmodeldemo

import android.log.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.Executors

class ExecutorsViewModel : ViewModel() {
    val members = MutableLiveData<Array<String>>()

    @Suppress("HasPlatformType", "unused")
    fun setMembersExecutors(membersText: String) = Executors.newSingleThreadExecutor().submit(setMembersExecutorsRunner(membersText))

    @VisibleForTesting
    fun testSetMembersExecutorsRunner(membersText: String) = setMembersExecutorsRunner(membersText).run()

    private fun setMembersExecutorsRunner(membersText: String) = Runnable {
        Log.w("\t\tstart - setMembersAsync")
        Thread.sleep(1_000)
        Log.w("\t\tdelay - setMembersAsync")
        val playerArray = membersText.split(",").filter { it.isNotBlank() }.toTypedArray()
        Log.w("\t\tset membersText")
        members.value = playerArray
        Log.w("\t\tset membersText")
    }
}
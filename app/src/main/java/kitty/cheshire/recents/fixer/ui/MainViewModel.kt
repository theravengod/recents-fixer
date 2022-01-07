package kitty.cheshire.recents.fixer.ui

import android.app.ActivityManager
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kitty.cheshire.recents.fixer.App
import kitty.cheshire.recents.fixer.PixelLauncherKillerWorker
import timber.log.Timber
import java.time.Duration

class MainViewModel : ViewModel() {

    val workerState: LiveData<WorkInfo.State?> = WorkManager.getInstance(App.instance.applicationContext)
        .getWorkInfosByTagLiveData(WORKER_TAG)
        .map { workInfoList ->
            workInfoList.lastOrNull { WORKER_TAG in it.tags }?.state
        }
        .distinctUntilChanged()

    fun justKillPixelLauncher() {
        val am = App.instance.applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        am.killBackgroundProcesses(PIXEL_LAUNCHER_PACKAGE_NAME)
    }

    fun toggleWorkerTask() {
        if (workerState.value !in listOf(WorkInfo.State.ENQUEUED, WorkInfo.State.RUNNING)) {
            Timber.d("Need to start task")
            startWorkerTask()
        } else {
            Timber.d("Should stop task")
            stopWorkerTask()
        }
    }

    private fun startWorkerTask() {
        val workRequest = PeriodicWorkRequestBuilder<PixelLauncherKillerWorker>(Duration.ofMinutes(20))
            .addTag(WORKER_TAG)
            .build()

        WorkManager.getInstance(App.instance.applicationContext)
            .enqueueUniquePeriodicWork(WORKER_TAG, ExistingPeriodicWorkPolicy.REPLACE, workRequest)
        Timber.i("Work should be started")
    }

    private fun stopWorkerTask() {
        WorkManager.getInstance(App.instance.applicationContext)
            .cancelAllWorkByTag(WORKER_TAG)
        Timber.i("Work canceled")
    }

    companion object {
        const val PIXEL_LAUNCHER_PACKAGE_NAME = "com.google.android.apps.nexuslauncher"
        const val WORKER_TAG = "PixelLauncherKiller"
    }
}
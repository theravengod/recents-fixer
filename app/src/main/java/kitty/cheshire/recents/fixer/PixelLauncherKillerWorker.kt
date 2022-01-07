package kitty.cheshire.recents.fixer

import android.app.ActivityManager
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class PixelLauncherKillerWorker(private val appContext: Context, workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        var actionResult = false
        kotlin.runCatching {
            (appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .killBackgroundProcesses("com.google.android.apps.nexuslauncher")
        }.onSuccess {
            actionResult = true
        }.onFailure {
            actionResult = false
        }

        return if (actionResult) Result.success() else Result.failure()
    }
}
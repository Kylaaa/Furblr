package com.itreallyiskyler.furblr.workers

import android.content.Context
import androidx.work.*
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.util.thunks.FetchNotifications
import com.itreallyiskyler.furblr.util.thunks.FetchPageOfHome
import java.util.concurrent.TimeUnit

// A background worker that fetches the content for your feeds
class ContentFetcherWorker(appContext: Context, workerParameters: WorkerParameters)
    : Worker(appContext, workerParameters) {

    override fun doWork(): Result {
        // TODO : figure out how to make these requests synchronous and have the results return appropriately
        if (SingletonManager.get().AuthManager.isAuthenticated()) {
            FetchNotifications(false) // fetches Journals and Notifications
            FetchPageOfHome(0, 48, false) // fetches Submissions Feed
        }

        return Result.success()
    }

    companion object {
        // TODO : allow these values to be configurable
        private const val WORK_NAME : String = "CONTENT_FETCHING"
        private const val INTERVAL : Long = 60
        private val INTERVAL_UNITS : TimeUnit = TimeUnit.MINUTES

        fun scheduleWork(workManager: WorkManager) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .setRequiresDeviceIdle(true)
                .setRequiresStorageNotLow(true)
                .build()

            val fetchRequest =
                PeriodicWorkRequest.Builder(ContentFetcherWorker::class.java, INTERVAL, INTERVAL_UNITS)
                    .setConstraints(constraints)
                    .setInitialDelay(INTERVAL, INTERVAL_UNITS)
                    .build()

            val existingWorkPolicy = ExistingPeriodicWorkPolicy.KEEP

            workManager.enqueueUniquePeriodicWork(WORK_NAME, existingWorkPolicy, fetchRequest)
        }
    }
}
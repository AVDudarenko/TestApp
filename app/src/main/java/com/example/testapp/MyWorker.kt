package com.example.testapp

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class MyWorker(
	context: Context,
	private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

	override fun doWork(): Result {
		val page = workerParameters.inputData.getInt(PAGE, 0)
		for (i in 0 until 10) {
			Thread.sleep(1000)
			Log.d("MY_TAG", "$i $page")
		}
		return Result.success()
	}

	companion object {
		private const val PAGE = "page"
		const val WORK_NAME = "work name"

		fun makeRequest(page: Int): OneTimeWorkRequest {
			return OneTimeWorkRequestBuilder<MyWorker>()
				.setInputData(workDataOf(PAGE to page))
				.setConstraints(makeConstraints())
				.build()
		}

		private fun makeConstraints(): Constraints {
			return Constraints.Builder()
				.setRequiresCharging(true)
				.build()
		}
	}
}
package eu.pw.messageboard

import android.app.Application
import eu.pw.messageboard.di.appModule
import eu.pw.messageboard.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class MessageBoardApp : Application() {
	override fun onCreate() {
		super.onCreate()
		if (BuildConfig.DEBUG) {
			Timber.plant(Timber.DebugTree())
		}
		startKoin {
			androidLogger()
			androidContext(this@MessageBoardApp)
			modules(
				appModule,
				viewModelModule
			)
		}
	}

}

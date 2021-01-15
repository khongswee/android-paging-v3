package com.kho.pagedv3

import android.app.Application
import com.kho.pagedv3.core.RemoteFactory
import com.kho.pagedv3.core.UserApi
import com.kho.pagedv3.core.UserRepository
import com.kho.pagedv3.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {

    private val remoteModule = module {
        single<UserApi> { RemoteFactory().createRemoteApi("https://gorest.co.in/") }
    }

    private val viewModelModule = module {
        factory { MainViewModel(get()) }
    }

    private val repositoryModule = module {
        single { UserRepository(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(remoteModule,viewModelModule,repositoryModule)
        }
    }
} 
package com.diogomenezes.jetpackarchitcture.di

import android.app.Application
import com.diogomenezes.jetpackarchitcture.BaseApplication
import com.diogomenezes.jetpackarchitcture.session.SessionManager
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuildersModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    val sessionManager: SessionManager // must add here b/c injecting into abstract class

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
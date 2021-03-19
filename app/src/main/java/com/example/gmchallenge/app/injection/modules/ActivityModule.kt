package com.example.gmchallenge.app.injection.modules

import com.example.gmchallenge.app.injection.scopes.ActivityScope
import com.example.gmchallenge.presentation.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Module
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity

}
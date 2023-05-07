package com.blogspot.soyamr.recipes3.app.hilt.utils

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    fun getPhoneNumberUtil(@ApplicationContext context: Context): PhoneNumberUtil =
        PhoneNumberUtil.createInstance(context)
}
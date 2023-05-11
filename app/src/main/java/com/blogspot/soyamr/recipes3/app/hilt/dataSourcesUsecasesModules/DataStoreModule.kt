package com.blogspot.soyamr.recipes3.app.hilt.dataSourcesUsecasesModules;

import android.content.Context
import com.blogspot.soyamr.data.local.datastore.DataStoreSourceImpl
import com.blogspot.soyamr.domain.dataStore.*
import com.blogspot.soyamr.domain.recipe.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {
    // region data sources
    companion object {
        @Provides
        fun provideDataStoreSource(
            @ApplicationContext context: Context
        ): DataStoreSource = DataStoreSourceImpl(context)
    }
    // endregion

    // region use cases
    // endregion
}

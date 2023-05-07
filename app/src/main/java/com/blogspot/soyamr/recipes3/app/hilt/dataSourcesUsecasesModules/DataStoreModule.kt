package com.blogspot.soyamr.recipes3.app.hilt.dataSourcesUsecasesModules;

import android.content.Context
import com.blogspot.soyamr.data.local.datastore.DataStoreSourceImpl
import com.blogspot.soyamr.domain.auth.*
import com.blogspot.soyamr.domain.dataStore.*
import dagger.Binds
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
    @Binds
    fun bindBlockingGetBaseUrlUseCase(impl: BlockingGetBaseUrlUseCaseImpl): BlockingGetBaseUrlUseCase

    @Binds
    fun bindObserveAppEnvironmentUseCase(impl: ObserveAppEnvironmentUseCaseImpl): ObserveAppEnvironmentUseCase

    @Binds
    fun bindUpdateAppEnvironmentUseCase(impl: UpdateAppEnvironmentUseCaseImpl): UpdateAppEnvironmentUseCase
    // endregion
}

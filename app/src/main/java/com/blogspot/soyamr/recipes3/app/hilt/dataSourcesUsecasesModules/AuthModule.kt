package com.blogspot.soyamr.recipes3.app.hilt.dataSourcesUsecasesModules;

import com.blogspot.soyamr.data.db.auth.AuthDbDataSourceImpl
import com.blogspot.soyamr.data.net.auth.AuthRemoteDataSourceImpl
import com.blogspot.soyamr.domain.auth.LoginUseCase
import com.blogspot.soyamr.domain.auth.LoginUseCaseImpl
import com.blogspot.soyamr.domain.auth.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {
    // region data sources
    @Binds
    fun bindAuthRemoteDataSource(impl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    fun bindAuthDbDataSource(impl: AuthDbDataSourceImpl): AuthDbDataSource
    // endregion

    // region use cases
    @Binds
    fun bindClearSessionUseCase(impl: ClearSessionUseCaseImpl): ClearSessionUseCase

    @Binds
    fun bindGetCurrentSessionUseCase(impl: GetCurrentSessionUseCaseImpl): GetCurrentSessionUseCase

    @Binds
    fun bindLogoutUseCase(impl: LogoutUseCaseImpl): LogoutUseCase

    @Binds
    fun bindObserveSessionUseCase(impl: ObserveSessionUseCaseImpl): ObserveSessionUseCase

    @Binds
    fun bindUpdateTokensUseCase(impl: UpdateTokensUseCaseImpl): UpdateTokensUseCase

    @Binds
    fun bindLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase
    // endregion
}

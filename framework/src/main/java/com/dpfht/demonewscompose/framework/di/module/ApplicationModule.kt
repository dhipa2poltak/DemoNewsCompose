package com.dpfht.demonewscompose.framework.di.module

import com.dpfht.demonewscompose.data.datasource.LocalDataSource
import com.dpfht.demonewscompose.data.datasource.RemoteDataSource
import com.dpfht.demonewscompose.data.repository.AppRepositoryImpl
import com.dpfht.demonewscompose.domain.repository.AppRepository
import com.dpfht.demonewscompose.framework.navigation.NavigationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

  @Singleton
  @Provides
  fun providesNavigationService() = NavigationService()

  @Provides
  @Singleton
  fun provideAppRepository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource): AppRepository {
    return AppRepositoryImpl(localDataSource, remoteDataSource)
  }
}

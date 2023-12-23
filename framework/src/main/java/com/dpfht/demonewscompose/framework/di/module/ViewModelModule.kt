package com.dpfht.demonewscompose.framework.di.module

import com.dpfht.demonewscompose.domain.repository.AppRepository
import com.dpfht.demonewscompose.domain.usecase.GetArticlesUseCase
import com.dpfht.demonewscompose.domain.usecase.GetArticlesUseCaseImpl
import com.dpfht.demonewscompose.domain.usecase.GetCategoriesUseCase
import com.dpfht.demonewscompose.domain.usecase.GetCategoriesUseCaseImpl
import com.dpfht.demonewscompose.domain.usecase.GetSourcesUseCase
import com.dpfht.demonewscompose.domain.usecase.GetSourcesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

  @Provides
  fun provideGetCategoriesUseCase(appRepository: AppRepository): GetCategoriesUseCase {
    return GetCategoriesUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetSourcesUseCase(appRepository: AppRepository): GetSourcesUseCase {
    return GetSourcesUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetArticlesUseCase(appRepository: AppRepository): GetArticlesUseCase {
    return GetArticlesUseCaseImpl(appRepository)
  }
}

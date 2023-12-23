package com.dpfht.demonewscompose.framework.di.module

import android.content.Context
import com.dpfht.demonewscompose.data.datasource.LocalDataSource
import com.dpfht.demonewscompose.data.datasource.RemoteDataSource
import com.dpfht.demonewscompose.framework.BuildConfig
import com.dpfht.demonewscompose.framework.Config
import com.dpfht.demonewscompose.framework.data.datasource.local.LocalDataSourceImpl
import com.dpfht.demonewscompose.framework.data.datasource.remote.rest.AuthInterceptor
import com.dpfht.demonewscompose.framework.data.datasource.remote.rest.RestService
import com.dpfht.demonewscompose.framework.data.datasource.remote.rest.UnsafeOkHttpClient
import com.dpfht.demonewscompose.framework.data.datasource.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  @Singleton
  fun provideCertificatePinner(): CertificatePinner {
    return CertificatePinner.Builder()
      .add("newsapi.org", "sha256/UmhcQTxjIQ7hbNRvTDeFt5LId41clz5KDOcuyIP+fd4=")
      .add("newsapi.org", "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
      .add("newsapi.org", "sha256/Y9mvm0exBk1JoQ57f9Vm28jKo5lFm/woKcVxrYxu80o=")
      .build()
  }

  @Provides
  @Singleton
  fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
  }

  @Provides
  @Singleton
  fun provideClient(certificatePinner: CertificatePinner): OkHttpClient {
    if (BuildConfig.DEBUG) {
      return UnsafeOkHttpClient.getUnsafeOkHttpClient()
    }

    val httpClientBuilder = OkHttpClient()
      .newBuilder()
      .certificatePinner(certificatePinner)

    httpClientBuilder.addInterceptor(AuthInterceptor())

    return httpClientBuilder.build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(Config.API_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttpClient)
      .build()
  }

  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): RestService {
    return retrofit.create(RestService::class.java)
  }

  @Provides
  @Singleton
  fun provideRemoteDataSource(@ApplicationContext context: Context, restService: RestService): RemoteDataSource {
    return RemoteDataSourceImpl(context, restService)
  }

  @Provides
  @Singleton
  fun provideLocalDataSource(@ApplicationContext context: Context): LocalDataSource {
    return LocalDataSourceImpl(context)
  }
}

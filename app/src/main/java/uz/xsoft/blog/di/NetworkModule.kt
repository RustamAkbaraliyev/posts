package uz.xsoft.blog.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.xsoft.blog.BuildConfig.BASE_URL
import uz.xsoft.blog.data.source.remote.api.PostApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @[Provides Singleton]
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @[Provides Singleton]
    fun providePostApi(retrofit: Retrofit): PostApi = retrofit.create(PostApi::class.java)


    @[Provides Singleton]
    fun provideClient(@ApplicationContext context: Context): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(ChuckerInterceptor.Builder(context).build())
        .build()

}
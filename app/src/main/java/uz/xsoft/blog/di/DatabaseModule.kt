package uz.xsoft.blog.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.xsoft.blog.data.source.local.daos.PostDao
import uz.xsoft.blog.data.source.local.room.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @[Singleton Provides]
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()

    @Provides
    fun providePostDao(appDatabase: AppDatabase): PostDao = appDatabase.postDao()
}
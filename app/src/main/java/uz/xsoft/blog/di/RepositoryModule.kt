package uz.xsoft.blog.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.xsoft.blog.domain.repositories.PostRepository
import uz.xsoft.blog.domain.repositories.impl.PostRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindPostRepository(impl: PostRepositoryImpl): PostRepository
}
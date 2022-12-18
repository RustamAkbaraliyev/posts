package uz.xsoft.blog.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.xsoft.blog.domain.usecases.DashboardUseCase
import uz.xsoft.blog.domain.usecases.FavUseCase
import uz.xsoft.blog.domain.usecases.impl.DashboardUseCaseImpl
import uz.xsoft.blog.domain.usecases.impl.FavUseCaseImpl
// this is interactors but named useCase
@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindDashboardUseCase(impl: DashboardUseCaseImpl): DashboardUseCase

    @Binds
    fun bindFavUseCase(impl: FavUseCaseImpl): FavUseCase

}
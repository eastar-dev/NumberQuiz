package dev.eastar.numberquiz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dev.eastar.numberquiz.data.repo.GameRepository
import dev.eastar.numberquiz.data.repo.GameRepositoryImpl
import dev.eastar.numberquiz.data.source.GeneratorRandomNumberSource
import dev.eastar.numberquiz.data.source.GeneratorRandomNumberSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object GameRepositoryModule {
    @Provides
    fun provideGameRepository(
        generatorRandomNumberSource: GeneratorRandomNumberSource
    ): GameRepository {
        return GameRepositoryImpl(generatorRandomNumberSource)
    }
}


@Module
@InstallIn(SingletonComponent::class)
object GeneratorRandomNumberSourceModule {
    @Singleton
    @Provides
    fun provideGeneratorRandomNumberSource(): GeneratorRandomNumberSource {
        return GeneratorRandomNumberSourceImpl()
    }
}

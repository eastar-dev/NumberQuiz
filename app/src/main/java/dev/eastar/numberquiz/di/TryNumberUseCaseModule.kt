package dev.eastar.numberquiz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dev.eastar.domain.TryNumberUseCase
import dev.eastar.domain.TryNumberUseCaseImpl
import dev.eastar.repository.GameRepository
import dev.eastar.repository.GameRepositoryImpl
import dev.eastar.source.GeneratorRandomNumberSource
import dev.eastar.source.GeneratorRandomNumberSourceImpl
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object TryNumberUseCaseModule {
    @Provides
    fun provideGameDomains(
        gameRepository: GameRepository
    ): TryNumberUseCase {
        return TryNumberUseCaseImpl(gameRepository)
    }
}

@Module
@InstallIn(SingletonComponent::class)
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

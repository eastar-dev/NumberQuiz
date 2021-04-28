package dev.eastar.numberquiz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dev.eastar.repository.GameRepository
import dev.eastar.repository.GameRepositoryImpl
import dev.eastar.source.GeneratorRandomNumberSource
import dev.eastar.source.GeneratorRandomNumberSourceImpl
import dev.eastar.source.LocalGameData
import dev.eastar.source.LocalGameDataImpl
import dev.eastar.usecase.GameRoundUseCase
import dev.eastar.usecase.RoundResultUseCase
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object TryNumberUseCaseModule {
    @Provides
    fun provideGameDomains(
        gameRepository: GameRepository,
        roundResultUseCase: RoundResultUseCase
    ): GameRoundUseCase {
        return GameRoundUseCase(gameRepository, roundResultUseCase)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object GameRepositoryModule {
    @Provides
    fun provideGameRepository(
        generatorRandomNumberSource: GeneratorRandomNumberSource,
        localGameData: LocalGameData
    ): GameRepository {
        return GameRepositoryImpl(generatorRandomNumberSource, localGameData)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object LocalGameDataModule {
    @Singleton
    @Provides
    fun provideLocalGame(): LocalGameData {
        return LocalGameDataImpl()
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

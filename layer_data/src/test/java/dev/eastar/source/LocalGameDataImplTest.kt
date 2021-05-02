package dev.eastar.source

import dev.eastar.entity.GameEntity
import junit.util.isit
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


internal class LocalGameDataImplTest {
    @Test
    fun setGame() {
        //given
        val localGameData = LocalGameDataImpl()
        //when
        val game = GameEntity(5)
        localGameData.setGame(game)
        val actual = localGameData.gameEntity
        //then
        MatcherAssert.assertThat(actual, isit(game))
    }

    @Test
    fun getGame() {
        //given
        val localGameData = LocalGameDataImpl()
        val game = GameEntity(5)
        localGameData.gameEntity = game
        //when
        val actual = localGameData.getGame()
        //then
        MatcherAssert.assertThat(actual, isit(game))
    }
}
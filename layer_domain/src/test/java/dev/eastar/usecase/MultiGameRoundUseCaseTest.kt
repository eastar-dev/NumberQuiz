package dev.eastar.usecase

import dev.eastar.entity.GameMulti
import dev.eastar.repository.GameRepository
import junit.util.isit
import junit.util.mock
import junit.util.whenever
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsNull.nullValue
import org.junit.jupiter.api.*
import kotlin.random.Random

@DisplayName("멀티게임\"이몽룡\", \"성춘향\", \"변사또\"으로 진행시 ")
internal class MultiGameRoundUseCaseTest {

    private lateinit var multiGameRoundUseCase: MultiGameRoundUseCase
    private val gameRepository: GameRepository by lazy { mock() }

    @BeforeEach
    fun setUp() {
        whenever(gameRepository.getGame()).thenReturn(GameMulti(CORRECT_ANSWER, arrayOf("이몽룡", "성춘향", "변사또")))
        multiGameRoundUseCase = MultiGameRoundUseCase(GameRoundUseCase(gameRepository, RoundResultUseCase()))
    }

    @Test
    @DisplayName("1번에정답이면 winner=이몽룡")
    fun invoke_round1() {
        //given
        val case = multiGameRoundUseCase

        //when
        val actual = case(CORRECT_ANSWER)

        //then
        MatcherAssert.assertThat(actual.winner, isit("이몽룡"))
    }

    @Test
    @DisplayName("2번에정답이면 winner=성춘향")
    fun invoke_round2() {
        //given
        val case = multiGameRoundUseCase

        //when
        case(WARN_ANSWER)
        val actual = case(CORRECT_ANSWER)

        //then
        MatcherAssert.assertThat(actual.winner, isit("성춘향"))
    }

    @Test
    @DisplayName("3번에정답이면 winner=변사또")
    fun invoke_round3() {
        //given
        val case = multiGameRoundUseCase

        //when
        case(WARN_ANSWER)
        case(WARN_ANSWER)
        val actual = case(CORRECT_ANSWER)

        //then
        MatcherAssert.assertThat(actual.winner, isit("변사또"))
    }

    @Test
    @DisplayName("3번에오답이면 winner=null")
    fun invoke_round3_warn() {
        //given
        val case = multiGameRoundUseCase

        //when
        case(WARN_ANSWER)
        case(WARN_ANSWER)
        val actual = case(WARN_ANSWER)

        //then
        MatcherAssert.assertThat(actual.winner, nullValue())
    }

    @Disabled
    @Deprecated("안좋은 test case : test code에 로직 자체도 검증력이 필요한듣하다.")
    @RepeatedTest(1)
    @DisplayName("n번에 정답이면 ...????")
    fun invoke_false() {
        //given
        val case = multiGameRoundUseCase

        //when
        val n = Random.nextInt(1, 101)
        repeat(n - 1) {
            case(WARN_ANSWER)
        }
        val actual = case(CORRECT_ANSWER)

        //then
        MatcherAssert.assertThat(actual.winner, isit(arrayOf("이몽룡", "성춘향", "변사또")[(actual.roundCount - 1) % 3]))
    }
}
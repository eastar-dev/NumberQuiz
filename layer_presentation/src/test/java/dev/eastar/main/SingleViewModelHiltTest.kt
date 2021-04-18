package dev.eastar.main

import android.util.InstantExecutorExtension
import android.util.MainCoroutineExtension
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.eastar.entity.TryResultEntity
import dev.eastar.repository.GameRepository
import dev.eastar.usecase.TryNumberUseCase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import javax.inject.Inject


@HiltAndroidTest
@ExtendWith(InstantExecutorExtension::class)
class SingleViewModelHiltTest {
    //@get:Rule
    //var hiltRule = HiltAndroidRule(this)

    companion object {
        @JvmField
        @RegisterExtension
        val hiltRule = HiltAndroidRule(this)
    }


    @Inject
    lateinit var gameRepository: GameRepository
    private lateinit var tryNumberUseCase: TryNumberUseCase

    @BeforeEach
    fun setUp() {
        hiltRule.inject()
        tryNumberUseCase = TryNumberUseCase(gameRepository)
    }

    @Test
    fun tryNumber() {
        //given
        val viewModel = SingleViewModel(tryNumberUseCase)

        //when
        viewModel.tryResultEntity.observeForever { }
        viewModel.gameEnd.observeForever {}
        viewModel.tryingNumber.value = "2"
        viewModel.tryNumber()

        //then
        assertAll({
            val actual = viewModel.tryResultEntity.value
            assertThat(actual, `is`(TryResultEntity.low))
        }, {
            val actual = viewModel.gameEnd.value
            assertThat(actual, `is`(nullValue()))
        })
    }

//    @ParameterizedTest
//    @CsvSource(value = ["1,0", "3,0", "5,1", "7,2", "9,2"])
//    fun tryNumber(number: String, result: Int) {
//        //given
//        val viewModel = SingleViewModel(tryNumberUseCase)
//        //when
//        val observer = Observer<TryResultEntity> {}
//        viewModel.tryResultEntity.observeForever(observer)
//        viewModel.tryingNumber.value = number
//        viewModel.tryNumber()
//
//        try {
//            //then
//            val TryResultEntity = TryResultEntity.values()[result]
//            val actual = viewModel.tryResultEntity.value
//            assertThat(actual, `is`(TryResultEntity))
//
//        } finally {
//            // Whatever happens, don't forget to remove the observer!
//            viewModel.tryResultEntity.removeObserver(observer)
//        }
//    }
//
//    //    @ValueSource(ints = [1,3,5,7,9])
////    @ParameterizedTest
////    @CsvSource(value = ["1,-1", "3,-1", "5,0", "7,+1", "9,+1"])
////    fun signmunTest(number: Int, result: Int) {
////        //given
////        val viewModel = SingleViewModel(tryNumberUseCase)
////        //when
////        val actual = signumTest(number)
////        //then
////        assertThat(actual, `is`(result))
////
////    }
//
//    @Test
//    fun tryNumber_correct() {
//        //given
//        val viewModel = SingleViewModel(tryNumberUseCase)
//        //when
//        val observer = Observer<String> {}
//        viewModel.gameEnd.observeForever(observer)
//        viewModel.tryingNumber.value = "5"
//        viewModel.tryNumber()
//
//        try {
//            //then
//            val actual = viewModel.gameEnd.value
//            assertThat(actual, `is`("축하합니다. 총시도 횟수는 1번 입니다."))
//
//        } finally {
//            // Whatever happens, don't forget to remove the observer!
//            viewModel.gameEnd.removeObserver(observer)
//        }
//    }
//
//    @Test
//    fun tryNumber_correct2() {
//        //given
//        val viewModel = SingleViewModel(tryNumberUseCase)
//        //when
//        val observer = Observer<String> {}
//        viewModel.gameEnd.observeForever(observer)
//        arrayOf("1", "5").forEach {
//            viewModel.tryingNumber.value = it
//            viewModel.tryNumber()
//        }
//
//        try {
//            //then
//            val actual = viewModel.gameEnd.value
//            assertThat(actual, `is`("축하합니다. 총시도 횟수는 2번 입니다."))
//
//        } finally {
//            // Whatever happens, don't forget to remove the observer!
//            viewModel.gameEnd.removeObserver(observer)
//        }
//    }
//
//    @Test
//    fun tryNumber_correct3() {
//        //given
//        val viewModel = SingleViewModel(tryNumberUseCase)
//        //when
//        val observer = Observer<String> {}
//        viewModel.gameEnd.observeForever(observer)
//        arrayOf("1", "2", "5").forEach {
//            viewModel.tryingNumber.value = it
//            viewModel.tryNumber()
//        }
//
//        try {
//            //then
//            val actual = viewModel.gameEnd.value
//            assertThat(actual, `is`("축하합니다. 총시도 횟수는 3번 입니다."))
//
//        } finally {
//            // Whatever happens, don't forget to remove the observer!
//            viewModel.gameEnd.removeObserver(observer)
//        }
//    }
}
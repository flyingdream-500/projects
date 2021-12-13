package com.example.finalproject.utils.extensions

import com.example.finalproject.utils.extensions.BaseExtensions.baseRatedBalance
import com.example.finalproject.utils.extensions.BaseExtensions.scaleTo2Symbols
import com.example.finalproject.utils.extensions.BaseExtensions.scaleTo3Symbols
import com.example.finalproject.utils.extensions.BaseExtensions.targetRatedBalance
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.RoundingMode

/**
 * Тестирование базовых методов округления
 */
class BaseExtensionsTest {

    companion object {

        // scaleTo3Symbols arguments
        private const val EXPECTED_3_SYMBOLS_ARGUMENT1 = 1.257f
        private const val EXPECTED_3_SYMBOLS_ARGUMENT2 = 1.257f
        private const val EXPECTED_3_SYMBOLS_ARGUMENT3 = 1.256f
        private const val EXPECTED_3_SYMBOLS_ARGUMENT4 = 0.009f

        private const val TESTED_3_SYMBOLS_ARGUMENT1 = 1.2569f
        private const val TESTED_3_SYMBOLS_ARGUMENT2 = 1.2565f
        private const val TESTED_3_SYMBOLS_ARGUMENT3 = 1.2564f
        private const val TESTED_3_SYMBOLS_ARGUMENT4 = 0.0089f


        // scaleTo2Symbols arguments
        private const val EXPECTED_2_SYMBOLS_ARGUMENT1 = 1.26f
        private const val EXPECTED_2_SYMBOLS_ARGUMENT2 = 1.25f

        private const val TESTED_2_SYMBOLS_ARGUMENT1 = 1.2550f
        private const val TESTED_2_SYMBOLS_ARGUMENT2 = 1.2549f


        // baseRatedBalance arguments
        private const val EXPECTED_BASE_RATED_ARGUMENT1 = 25.80f
        private const val EXPECTED_BASE_RATED_ARGUMENT2 = 41.75f
        private const val EXPECTED_BASE_RATED_ARGUMENT3 = 10.86f

        private const val TESTED_BASE_INPUT_ARGUMENT1 = 2.58f
        private const val TESTED_BASE_INPUT_ARGUMENT2 = 50f
        private const val TESTED_BASE_INPUT_ARGUMENT3 = 13f

        private const val TESTED_BASE_RATE_ARGUMENT1 = 10f
        private const val TESTED_BASE_RATE_ARGUMENT2 = 0.835f
        private const val TESTED_BASE_RATE_ARGUMENT3 = 0.835f

        // targeconst tRatedBalance arguments
        private const val EXPECTED_TARGET_RATED_ARGUMENT1 = 0.25f
        private const val EXPECTED_TARGET_RATED_ARGUMENT2 = 59.88f
        private const val EXPECTED_TARGET_RATED_ARGUMENT3 = 15.56f

        private const val TESTED_TARGET_INPUT_ARGUMENT1 = 2.58f
        private const val TESTED_TARGET_INPUT_ARGUMENT2 = 50f
        private const val TESTED_TARGET_INPUT_ARGUMENT3 = 13f

        private const val TESTED_TARGET_RATE_ARGUMENT1 = 10f
        private const val TESTED_TARGET_RATE_ARGUMENT2 = 0.835f
        private const val TESTED_TARGET_RATE_ARGUMENT3 = 0.835f

    }





    /**
     * Тестирование метода [scaleTo3Symbols]
     */
    @Test
    fun scaleTo3SymbolsTest1() {
        val expectedValue = EXPECTED_3_SYMBOLS_ARGUMENT1
        val result = TESTED_3_SYMBOLS_ARGUMENT1.scaleTo3Symbols()
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование метода [scaleTo3Symbols]
     */
    @Test
    fun scaleTo3SymbolsTest2() {
        val expectedValue = EXPECTED_3_SYMBOLS_ARGUMENT2
        val result = TESTED_3_SYMBOLS_ARGUMENT2.scaleTo3Symbols()
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование метода [scaleTo3Symbols]
     */
    @Test
    fun scaleTo3SymbolsTest3() {
        val expectedValue = EXPECTED_3_SYMBOLS_ARGUMENT3
        val result = TESTED_3_SYMBOLS_ARGUMENT3.scaleTo3Symbols()
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование метода [scaleTo3Symbols]
     */
    @Test
    fun scaleTo3SymbolsTest4() {
        val expectedValue = EXPECTED_3_SYMBOLS_ARGUMENT4
        val result = TESTED_3_SYMBOLS_ARGUMENT4.scaleTo3Symbols()
        assertEquals(expectedValue, result)
    }


    /**
     * Тестирование метода [scaleTo2Symbols]
     */
    @Test
    fun scaleTo2SymbolsUpTest1() {
        val expectedValue = EXPECTED_2_SYMBOLS_ARGUMENT1
        val result = TESTED_2_SYMBOLS_ARGUMENT1.scaleTo2Symbols(RoundingMode.HALF_UP)
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование метода [scaleTo2Symbols]
     */
    @Test
    fun scaleTo2SymbolsUpTest2() {
        val expectedValue = EXPECTED_2_SYMBOLS_ARGUMENT2
        val result = TESTED_2_SYMBOLS_ARGUMENT2.scaleTo2Symbols(RoundingMode.HALF_UP)
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование метода [scaleTo2Symbols]
     */
    @Test
    fun scaleTo2SymbolsDownTest1() {
        val expectedValue = EXPECTED_2_SYMBOLS_ARGUMENT2
        val result = TESTED_2_SYMBOLS_ARGUMENT1.scaleTo2Symbols(RoundingMode.FLOOR)
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование метода [scaleTo2Symbols]
     */
    @Test
    fun scaleTo2SymbolsDownTest2() {
        val expectedValue = EXPECTED_2_SYMBOLS_ARGUMENT2
        val result = TESTED_2_SYMBOLS_ARGUMENT2.scaleTo2Symbols(RoundingMode.FLOOR)
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование метода [baseRatedBalance]
     */
    @Test
    fun baseRatedBalanceTest1() {
        val expectedValue = EXPECTED_BASE_RATED_ARGUMENT1
        val result = baseRatedBalance(TESTED_BASE_INPUT_ARGUMENT1, TESTED_BASE_RATE_ARGUMENT1, RoundingMode.HALF_UP)
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование метода [baseRatedBalance]
     */
    @Test
    fun baseRatedBalanceTest2() {
        val expectedValue = EXPECTED_BASE_RATED_ARGUMENT2
        val result = baseRatedBalance(TESTED_BASE_INPUT_ARGUMENT2, TESTED_BASE_RATE_ARGUMENT2, RoundingMode.HALF_UP)
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование метода [baseRatedBalance]
     */
    @Test
    fun baseRatedBalanceTest3() {
        val expectedValue = EXPECTED_BASE_RATED_ARGUMENT3
        val result = baseRatedBalance(TESTED_BASE_INPUT_ARGUMENT3, TESTED_BASE_RATE_ARGUMENT3, RoundingMode.HALF_UP)
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование метода [targetRatedBalance]
     */
    @Test
    fun targetRatedBalanceTest1() {
        val expectedValue = EXPECTED_TARGET_RATED_ARGUMENT1
        val result = targetRatedBalance(TESTED_TARGET_INPUT_ARGUMENT1, TESTED_TARGET_RATE_ARGUMENT1, RoundingMode.FLOOR)
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование метода [targetRatedBalance]
     */
    @Test
    fun targetRatedBalanceTest2() {
        val expectedValue = EXPECTED_TARGET_RATED_ARGUMENT2
        val result = targetRatedBalance(TESTED_TARGET_INPUT_ARGUMENT2, TESTED_TARGET_RATE_ARGUMENT2, RoundingMode.FLOOR)
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование метода [targetRatedBalance]
     */
    @Test
    fun targetRatedBalanceTest3() {
        val expectedValue = EXPECTED_TARGET_RATED_ARGUMENT3
        val result = targetRatedBalance(TESTED_TARGET_INPUT_ARGUMENT3, TESTED_TARGET_RATE_ARGUMENT3, RoundingMode.FLOOR)
        assertEquals(expectedValue, result)
    }

}
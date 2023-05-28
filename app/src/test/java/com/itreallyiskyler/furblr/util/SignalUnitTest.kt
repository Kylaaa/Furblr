package com.itreallyiskyler.furblr.util

import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test

class SignalUnitTest {
    private fun createTestSignal() : Signal<Int> {
        val testSignal = Signal<Int>()
        testSignal.isAsync = false
        return testSignal
    }

    @Test
    fun connect_connectsACallback() {
        var timesCalled : Int = 0

        val testSignal = createTestSignal()

        val testValue = 1
        testSignal.fire(testValue)
        assertEquals(timesCalled, 0)

        // connect a callback
        var foundValue : Int = -1;
        testSignal.connect { suppliedVal : Int ->
            run {
                timesCalled++
                foundValue = suppliedVal
            }
        }
        testSignal.fire(testValue)
        assertEquals(timesCalled, 1)
        assertEquals(foundValue, testValue)
    }

    @Test
    fun connect_allowsMultipleConnections() {
        var foundValues : MutableList<Int> = mutableListOf()

        val testSignal = createTestSignal()

        testSignal.connect { suppliedVal : Int -> foundValues.add(suppliedVal) }
        testSignal.connect { suppliedVal : Int -> foundValues.add(suppliedVal) }
        testSignal.connect { suppliedVal : Int -> foundValues.add(suppliedVal) }

        val testValue = 0
        testSignal.fire(testValue)

        assertEquals(foundValues.count(), 3)
        foundValues.forEach { assertEquals(it, testValue) }
    }

    @Test
    fun connect_firesInOrderOfConnection() {
        var orderCalled : MutableList<Int> = mutableListOf()

        val testSignal = createTestSignal()

        testSignal.connect { _ : Int -> orderCalled.add(1) }
        testSignal.connect { _ : Int -> orderCalled.add(2) }

        testSignal.fire(0)

        assertEquals(orderCalled.count(), 2)
        assertEquals(orderCalled[0], 1)
        assertEquals(orderCalled[1], 2)
    }

    @Test
    fun connect_returnsACallbackToDisconnectTheCallback() {
        var timesCalled : Int = 0

        val testSignal = createTestSignal()

        val disconnectToken = testSignal.connect { _ : Int -> timesCalled++ }
        testSignal.fire(0)
        assertEquals(timesCalled, 1)

        timesCalled = 0
        disconnectToken()
        testSignal.fire(0)
        assertEquals(timesCalled, 0)
    }

    @Test()
    fun fire_executesTheGivenCallbackEveryTime() {
        var givenValues : MutableList<Int> = mutableListOf()

        val testSignal = createTestSignal()

        testSignal.connect { suppliedValue : Int -> givenValues.add(suppliedValue) }

        testSignal.fire(0)
        testSignal.fire(1)
        testSignal.fire(2)

        assertEquals(givenValues.count(), 3)
        assertEquals(givenValues[0], 0)
        assertEquals(givenValues[1], 1)
        assertEquals(givenValues[2], 2)
    }

    @Test
    fun disconnectAll_removesAllConnections() {
        var timesCalled : Int = 0

        val testSignal = createTestSignal()

        testSignal.connect { _ : Int -> timesCalled++ }
        testSignal.connect { _ : Int -> timesCalled++ }

        testSignal.fire(0)
        assertEquals(timesCalled, 2)

        timesCalled = 0
        testSignal.disconnectAll()
        testSignal.fire(0)
        assertEquals(timesCalled, 0)
    }

    @Test
    fun disconnectAll_disablesExistingDisconnectTokens() {
        var oldSignalCalled : Boolean = false

        val testSignal = createTestSignal()

        val disconnectToken = testSignal.connect { _ : Int -> oldSignalCalled = true }
        testSignal.fire(0)
        assertTrue(oldSignalCalled)

        oldSignalCalled = false
        testSignal.disconnectAll()
        testSignal.fire(0)
        assertFalse(oldSignalCalled)

        // create a new connection
        var newSignalCalled : Boolean = false
        testSignal.connect { _ : Int -> newSignalCalled = true }

        // disconnect the original connection, even though it's already been disconnected
        disconnectToken()

        // check that the new signal fires
        testSignal.fire(0)
        assertTrue(newSignalCalled)
    }
}
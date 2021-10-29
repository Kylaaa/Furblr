package com.itreallyiskyler.furblr

import android.view.inspector.PropertyReader
import com.itreallyiskyler.furblr.util.GenericCallback
import com.itreallyiskyler.furblr.util.Promise
import com.itreallyiskyler.furblr.util.PromiseState
import org.junit.Assert.*
import org.junit.Test

class PromiseUnitTest {
    @Test
    fun constructor_setsStateStarted() {
        val a = Promise(fun(_, _){})
        assertEquals(a.getPromiseState(), PromiseState.Started)
    }

    @Test
    fun constructor_resolvesSynchronously() {
        var timesCalled = 0

        Promise(fun(resolve, _){
            timesCalled += 1
            resolve(null)
        })

        assertEquals(timesCalled, 1)
    }

    //---------------------------------------------------------------------------------------------

    @Test
    fun resolve_setsStateResolved() {
        val a = Promise(fun(resolve, _){
            resolve(null)
        })
        assertEquals(a.getPromiseState(), PromiseState.Resolved)
    }

    @Test
    fun resolve_setsValue() {
        val a = Promise(fun(resolve, _){
            resolve(1)
        })
        assertEquals(a.getPromiseValue(), 1)
    }

    @Test
    fun resolve_cannotBeCalledMultipleTimes() {
        val a = Promise(fun(resolve, _){
            resolve(1)
            resolve(2)
        })
        assertEquals(a.getPromiseValue(), 1)
    }

    @Test
    fun resolve_cannotBeRejectedAfterwardsManually() {
        val a = Promise(fun(resolve, reject){
            resolve(1)
            reject(2)
        })
        assertEquals(a.getPromiseValue(), 1)
        assertEquals(a.getPromiseState(), PromiseState.Resolved)
    }

    @Test
    fun resolve_chainsOntoReturnedPromises() {
        val a = Promise(fun(resolve, _){
            resolve(Promise(fun(resolve2, _) {
                resolve2(2)
            }))
        })
        assertEquals(a.getPromiseValue(), 2)
        assertEquals(a.getPromiseState(), PromiseState.Resolved)

        val b = Promise(fun(resolve, _){
            resolve(Promise(fun(_, reject2) {
                reject2(3)
            }))
        })
        assertEquals(b.getPromiseValue(), 3)
        assertEquals(b.getPromiseState(), PromiseState.Rejected)
    }

    //---------------------------------------------------------------------------------------------

    @Test
    fun reject_setsStateRejected() {
        val a = Promise(fun(_, reject){
            reject(null)
        })
        assertEquals(a.getPromiseState(), PromiseState.Rejected)
    }

    @Test
    fun reject_setsValue() {
        val a = Promise(fun(_, reject){
            reject(1)
        })
        assertEquals(a.getPromiseValue(), 1)
    }

    @Test
    fun reject_cannotBeCalledMultipleTimes() {
        val a = Promise(fun(_, reject){
            reject(1)
            reject(2)
        })
        assertEquals(a.getPromiseValue(), 1)
    }

    @Test
    fun reject_cannotBeResolvedAfterwardsManually() {
        val a = Promise(fun(resolve, reject){
            reject(1)
            resolve(2)
        })
        assertEquals(a.getPromiseValue(), 1)
        assertEquals(a.getPromiseState(), PromiseState.Rejected)
    }

    //---------------------------------------------------------------------------------------------

    @Test
    fun then_returnsANewPromise() {
        val a = Promise(fun(resolve, _){
            resolve(1)
        })
        val b = a.then(fun(_) : Int {
            return 1
        })
        assertEquals(a.getPromiseValue(), b.getPromiseValue())
        assertNotEquals(a, b)
    }

    @Test
    fun then_storesReturnedValues() {
        val a = Promise(fun(resolve, _){
            resolve(1)
        }).then(fun(resolvedVal) : Int {
            return (resolvedVal as Int) + 1
        })
        assertEquals(a.getPromiseValue(), 2)

        val b = Promise(fun(resolve, _){
            resolve(1)
        }).then(fun(resolvedVal) : Int {
            return (resolvedVal as Int) + 1
        }).then(fun(resolvedVal) : Int {
            return (resolvedVal as Int) + 1
        })
        assertEquals(b.getPromiseValue(), 3)
    }

    @Test
    fun then_chainsOntoReturnedPromises() {
        val a = Promise(fun(resolve, _){
            resolve(1)
        }).then(fun(_) : Promise {
            return Promise(fun(resolve2, _) {
                resolve2(2)
            })
        })
        assertEquals(a.getPromiseValue(), 2)
        assertFalse(a.getPromiseValue()!! is Promise)
    }

    @Test
    fun then_chainsOntoQueuedAsyncPromises() {
        var resolverFunc1 : GenericCallback? = null
        var resolverFunc2 : GenericCallback? = null
        var callCount = 0
        var currentPromiseValue : Any? = null

        val a = Promise(fun(resolve, _){
            resolverFunc1 = resolve
            callCount += 1
        }).then(fun(promValue) : Promise {
            callCount += 1
            currentPromiseValue = promValue
            return Promise(fun(resolve2, _) {
                resolverFunc2 = resolve2
            })
        }).then(fun(promValue) : String {
            callCount += 1
            return "Test : $promValue"
        })

        assertEquals(callCount, 1)
        assertNull(resolverFunc2)
        assertNull(currentPromiseValue)
        assertEquals(a.getPromiseState(), PromiseState.Started)

        resolverFunc1!!(true)

        assertEquals(callCount, 2)
        assertEquals(currentPromiseValue, true)
        assertNotNull(resolverFunc2)
        assertEquals(a.getPromiseState(), PromiseState.Started)

        resolverFunc2!!(false)

        assertEquals(callCount, 3)
        assertEquals(a.getPromiseState(), PromiseState.Resolved)
        assertEquals(a.getPromiseValue(), "Test : false")
    }

    @Test
    fun then_queuesResponsesWhenNotYetCompleted() {
        var timesCalled : Int = 0
        var resolverFunc : GenericCallback? = null
        val a = Promise(fun(resolve, _) {
            resolverFunc = resolve
        })
        assertEquals(a.getPromiseState(), PromiseState.Started)

        a.then(fun(_) { timesCalled += 1 })
        a.then(fun(_) { timesCalled += 1 })
        a.then(fun(_) { timesCalled += 1 })
        assertEquals(timesCalled, 0)
        assertEquals(a.getObserversSuccess().size, 3)

        resolverFunc!!(null)
        assertEquals(timesCalled, 3)
    }

    //---------------------------------------------------------------------------------------------

    @Test
    fun promise_resolve_returnsResolvedPromise() {
        val a = Promise.resolve(1)
        assertEquals(a.getPromiseState(), PromiseState.Resolved)
        assertEquals(a.getPromiseValue(), 1)
    }

    //---------------------------------------------------------------------------------------------

    @Test
    fun promise_reject_returnsRejectedPromise() {
        val a = Promise.reject(1)
        assertEquals(a.getPromiseState(), PromiseState.Rejected)
        assertEquals(a.getPromiseValue(), 1)
    }

    //---------------------------------------------------------------------------------------------

    @Test
    fun promise_all_resolvesImmediatelyWithAnEmptyArray() {
        val a = Promise.all(emptyArray<Promise>())
        assertEquals(a.getPromiseState(), PromiseState.Resolved)
        assertEquals((a.getPromiseValue() as Array<*>).size, 0)
    }

    @Test
    fun promise_all_returnsAnArrayWithEachPromisesValues() {
        val a = Promise.all(arrayOf(
            Promise(fun(resolve, _) { resolve(3) }),
            Promise(fun(resolve, _) { resolve(2) }),
            Promise(fun(resolve, _) { resolve(1) })
        ))
        assertEquals(a.getPromiseState(), PromiseState.Resolved)

        val results : List<Int> = a.getPromiseValue() as List<Int>
        assertEquals(results[0], 3)
        assertEquals(results[1], 2)
        assertEquals(results[2], 1)
    }

    @Test
    fun promise_all_rejectsIfAnyFail() {
        val a = Promise.all(arrayOf(
            Promise(fun(resolve, _) { resolve(1) }),
            Promise(fun(resolve, _) { resolve(2) }),
            Promise(fun(_, reject) { reject(3) })
        ))
        assertEquals(a.getPromiseState(), PromiseState.Rejected)
    }
}
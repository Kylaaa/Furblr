package com.itreallyiskyler.furblr.util

import okhttp3.internal.toImmutableList
import kotlin.concurrent.thread

enum class PromiseState {
    Pending,
    Started,
    Resolved,
    Rejected,
}
typealias GenericCallback = (Any?)->Any?
typealias TypedCallback<T> = (T)->Any?

class Promise(action: (resolve: GenericCallback, reject: GenericCallback) -> Unit) {
    private var _promiseState : PromiseState = PromiseState.Pending
    private var _promiseValue : Any? = null
    private var _successObservers : MutableList<GenericCallback> = mutableListOf()
    private var _failureObservers : MutableList<GenericCallback> = mutableListOf()

    internal fun getPromiseState() : PromiseState { return _promiseState }
    internal fun getPromiseValue() : Any? { return _promiseValue }
    internal fun getObserversSuccess() : List<GenericCallback> { return _successObservers.toImmutableList() }
    internal fun getObserversFailure() : List<GenericCallback> { return _failureObservers.toImmutableList() }

    init {
        try {
            _promiseState = PromiseState.Started
            action(::_resolver, ::_rejector)
        } catch (ex: Exception) {
            reject(ex)
        }
    }
    private fun _resolver(resolvedValue : Any?){
        if (_promiseState != PromiseState.Started) {
            // cannot resolve multiple times
            //println("Promise tried to resolve twice!")
            return
        }

        // if the resolved value is a Promise, chain onto it
        if (resolvedValue is Promise) {
            resolvedValue.then(
                fun(result : Any?) {
                    _resolver(result)
                },
                fun(err : Any?){
                    _rejector(err)
                }
            )

            // escape and wait for the next result
            return
        }

        _promiseValue = resolvedValue
        _promiseState = PromiseState.Resolved

        for (callback in _successObservers) {
            callback(_promiseValue)
        }
    }
    private fun _rejector(rejectedValue : Any?) {
        if (_promiseState != PromiseState.Started) {
            // cannot reject multiple times
            return
        }

        _promiseValue = rejectedValue
        _promiseState = PromiseState.Rejected
    }
    private fun _createAdvancer(action : GenericCallback,
                                resolverFunc : GenericCallback,
                                rejectorFunc : GenericCallback) : GenericCallback {
        val advancer = fun(futurePromiseValue : Any?) {
            var result : Any?
            try {
                result = action(futurePromiseValue)
            }
            catch (ex : Exception) {
                rejectorFunc(ex)
                return
            }

            resolverFunc(result!!)
        }
        return advancer
    }

    fun then(successHandler : GenericCallback? = null, failureHandler : GenericCallback? = null) : Promise {
        val action = fun(resolveFunc : GenericCallback, rejectFunc : GenericCallback) {
            var successCallback = resolveFunc
            if (successHandler != null) {
                successCallback = _createAdvancer(successHandler, resolveFunc, rejectFunc)
            }

            var failureCallback = rejectFunc
            if (failureHandler != null) {
                failureCallback = _createAdvancer(failureHandler, resolveFunc, rejectFunc)
            }

            if (_promiseState == PromiseState.Resolved) {
                successCallback(_promiseValue)
            }
            else if(_promiseState == PromiseState.Rejected) {
                failureCallback(_promiseValue)
            }
            else {
                _successObservers.add(successCallback)
                _failureObservers.add(failureCallback)
            }
        }

        return Promise(action)
    }

    fun catch(failureHandler : GenericCallback) : Promise {
        return then(null, failureHandler)
    }

    companion object{
        fun resolve(value : Any) : Promise {
            val action = fun(resolveFunc : GenericCallback, _ : GenericCallback) {
                resolveFunc(value)
            }
            return Promise(action)
        }

        fun reject(value : Any) : Promise {
            val action = fun(_ : GenericCallback, rejectFunc : GenericCallback) {
                rejectFunc(value)
            }
            return Promise(action)
        }

        fun all(promises : Array<Promise>) : Promise {
            if (promises.isEmpty()) {
                return Promise.resolve(emptyArray<Any>())
            }

            val action = fun(resolverFunc : GenericCallback, rejectorFunc : GenericCallback) {
                val resolvedValues : MutableList<Any?> = MutableList<Any?>(promises.size) { null }
                var resolvedCount = 0

                val createPromiseResolver = fun(i : Int) : GenericCallback {
                    return fun(results : Any?) {
                        try {
                            resolvedValues.set(i, results)
                            resolvedCount++
                            if (resolvedCount == promises.size) {
                                resolverFunc(resolvedValues.toList())
                            }
                        }
                        catch (ex : Exception)
                        {
                            println(ex)
                            rejectorFunc(ex)
                        }
                    }
                }

                for (i in 0 .. (promises.size - 1)) {
                    promises[i].then(createPromiseResolver(i), rejectorFunc)
                }
            }
            return Promise(action)
        }
    }
}
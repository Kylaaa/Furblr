package com.itreallyiskyler.furblr.util

import kotlin.concurrent.thread

enum class PromiseState {
    Pending,
    Started,
    Resolved,
    Rejected,
}
typealias GenericCallback = (Any)->Any?
typealias TypedCallback<T> = (T)->Any?

class Promise(action: (resolve: GenericCallback, reject: GenericCallback) -> Unit) {
    private var _promiseState : PromiseState = PromiseState.Pending
    private lateinit var _promiseValue : Any
    private var _successObservers : MutableList<GenericCallback> = mutableListOf()
    private var _failureObservers : MutableList<GenericCallback> = mutableListOf()

    init {
        try {
            _promiseState = PromiseState.Started
            action(::_resolver, ::_rejector)
        } catch (ex: Exception) {
            reject(ex)
        }
    }
    private fun _resolver(resolvedValue : Any){
        if (_promiseState != PromiseState.Started) {
            // cannot resolve multiple times
            println("Promise tried to resolve twice!")
            return
        }

        // if the resolved value is a Promise, chain onto it
        if (resolvedValue === Promise) {
            (resolvedValue as Promise).then(
                fun(result) {
                    _resolver(result)
                },
                fun(err) {
                    _rejector(err)
                })

            // escape and wait for the next result
            return
        }

        _promiseValue = resolvedValue
        _promiseState = PromiseState.Resolved

        for (callback in _successObservers) {
            callback(_promiseValue)
        }
    }
    private fun _rejector(rejectedValue : Any) {
        if (_promiseState != PromiseState.Started) {
            // cannot reject multiple times
            return
        }

        _promiseValue = rejectedValue
        _promiseState = PromiseState.Rejected
    }
    private fun _createAdvancer(action : GenericCallback,
                                resolve : GenericCallback,
                                reject : GenericCallback) : GenericCallback {
        val advancer = fun(futurePromiseValue : Any) {
            var result : Any?
            try {
                result = action(futurePromiseValue)
            }
            catch (ex : Exception) {
                reject(ex)
                return
            }

            resolve(result!!)
        }
        return advancer
    }

    fun then(successHandler : GenericCallback?, failureHandler : GenericCallback?) : Promise {
        val action = fun(resolve : GenericCallback, reject : GenericCallback) {
            var successCallback = resolve
            if (successHandler != null) {
                successCallback = _createAdvancer(successHandler, resolve, reject)
            }

            var failureCallback = reject
            if (failureHandler != null) {
                failureCallback = _createAdvancer(failureHandler, resolve, reject)
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
            val action = fun(resolve : GenericCallback, _ : GenericCallback) {
                resolve(value)
            }
            return Promise(action)
        }

        fun reject(value : Any) : Promise {
            val action = fun(_ : GenericCallback, reject : GenericCallback) {
                reject(value)
            }
            return Promise(action)
        }

        fun all(promises : Array<Promise>) : Promise {
            if (promises.isEmpty()) {
                return Promise.resolve({})
            }

            val action = fun(resolve : GenericCallback, reject : GenericCallback) {
                val resolvedValues : MutableList<Any> = mutableListOf()
                var resolvedCount = 0

                val createPromiseResolver = fun(i : Int) : GenericCallback {
                    return fun(results : Any) {
                        resolvedValues[i] = results
                        resolvedCount++

                        if (resolvedCount == promises.size) {
                            resolve(resolvedValues.toList())
                        }
                    }
                }

                for (i in 1 .. promises.size) {
                    promises[i].then(createPromiseResolver(i), reject)
                }
            }
            return Promise(action)
        }
    }
}
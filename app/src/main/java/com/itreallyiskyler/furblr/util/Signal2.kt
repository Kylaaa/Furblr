package com.itreallyiskyler.furblr.util

import kotlin.concurrent.thread

class Signal2<T, U> {
    private var _nextId : Int = 0
    private var _connections : MutableMap<Int, (T, U) -> Unit> = mutableMapOf()

    fun connect(cmd : (T, U) -> Unit) : () -> Unit{
        _nextId += 1

        // hold onto the Callback
        _connections.put(_nextId, cmd)
        
        // return an object to disconnect the connection
        val nextIdCopy = _nextId
        return fun() : Unit {
            _connections.remove(nextIdCopy)
        }
    }

    fun fire(val1 : T, val2 : U)
    {
        // iterate over all of the connections and fire them with the supplied value
        for (pair in _connections) {
            val connection = pair.value
            thread(start = true) {
                connection(val1, val2)
            }
        }
    }

    fun disconnectAll() {
        _connections.clear()
        _nextId = 0
    }
}
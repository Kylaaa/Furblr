package com.itreallyiskyler.furblr.util

import kotlin.concurrent.thread

class Signal<T> {
    public var isAsync : Boolean = false
    private var _nextId : Int = 0
    private var _connections : MutableMap<Int, (T)->Unit> = mutableMapOf()

    fun connect(cmd : (T)->Unit) : ()->Unit {
        _nextId += 1

        // hold onto the Callback
        _connections.put(_nextId, cmd)

        // return an object to disconnect the connection
        val nextIdCopy = _nextId
        return fun() : Unit {
            if (_connections.containsKey(nextIdCopy)) {
                _connections.remove(nextIdCopy)
            }
        }
    }

    fun fire(value : T)
    {
        // iterate over all of the connections and fire them with the supplied value
        for (pair in _connections) {
            val connection = pair.value
            if (isAsync) {
                thread(start = true) {
                    connection(value)
                }
            }
            else {
                connection(value)
            }
        }
    }

    fun disconnectAll() {
        _connections.clear()
        // do not reset the counter to ensure that existing disconnect tokens no longer work
    }
}
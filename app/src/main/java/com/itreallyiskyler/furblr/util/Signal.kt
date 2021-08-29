package com.itreallyiskyler.furblr.util

import kotlin.concurrent.thread

class Signal<T> {
    private var _nextId : Int = 0;
    private var _connections : MutableMap<Int, CommandWithArgs1<Unit, T>> = mutableMapOf();

    fun connect(cmd : CommandWithArgs1<Unit, T>) : Command<Unit>{
        _nextId += 1;

        // hold onto the Callback
        _connections.put(_nextId, cmd);
        
        // return an object to disconnect the connection
        val nextIdCopy = _nextId;
        return object : Command<Unit> {
            override fun invoke() : Unit {
                _connections.remove(nextIdCopy)
            };
        }
    }

    fun fire(value : T)
    {
        // iterate over all of the connections and fire them with the supplied value
        for (pair in _connections) {
            val connection = pair.value;
            thread(start = true) {
                connection.invoke(value);
            }
        }
    }

    fun disconnectAll() {
        _connections.clear();
        _nextId = 0;
    }
}
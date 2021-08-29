package com.itreallyiskyler.furblr.util;

// a simple interface for passing functions as arguments
public interface Command<T> {
    T invoke();
}
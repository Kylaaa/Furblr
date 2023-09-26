package com.itreallyiskyler.furblr.util;

// Equivalent to (a : A, b: B) -> T
public interface CommandWithArgs2<T, A, B> {
    T invoke(A a, B b);
}

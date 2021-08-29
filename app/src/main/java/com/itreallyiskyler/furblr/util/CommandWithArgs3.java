package com.itreallyiskyler.furblr.util;

public interface CommandWithArgs3<T, A, B, C> {
    T invoke(A a, B b, C c);
}

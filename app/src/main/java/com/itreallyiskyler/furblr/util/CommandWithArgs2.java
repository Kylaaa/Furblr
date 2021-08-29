package com.itreallyiskyler.furblr.util;

public interface CommandWithArgs2<T, A, B> {
    T invoke(A a, B b);
}

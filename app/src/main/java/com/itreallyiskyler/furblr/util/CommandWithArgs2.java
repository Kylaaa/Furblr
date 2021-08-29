package com.itreallyiskyler.furblr.util;

public interface CommandWithArgs1<T, A> {
    T invoke(A a);
}

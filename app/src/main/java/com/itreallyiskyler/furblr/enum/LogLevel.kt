package com.itreallyiskyler.furblr.enum

enum class LogLevel(override val id : Int) : IValueAccessor<Int> {
    NONE(0),
    ERROR(1),
    WARNING(2),
    INFORMATION(3),
    TRACE(4)
}
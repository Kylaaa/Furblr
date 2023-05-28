package com.itreallyiskyler.furblr.enum

enum class SubmissionScrollDirection(override val id : String) : IValueAccessor<String> {
    DEFAULT(""),
    NEWEST("new"),
    OLDEST("old")
}
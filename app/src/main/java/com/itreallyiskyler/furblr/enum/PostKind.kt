package com.itreallyiskyler.furblr.enum

enum class PostKind(val id : Int) {
    Image(0),
    Journal(1),
    Writing(2),
    Music(3),
    Flash(4),
    Downloadable(5),
    Unknown(6);

    companion object {
        fun fromId(id : Int) : PostKind {
            return PostKind.values().first { it.id == id }
        }
    }
}
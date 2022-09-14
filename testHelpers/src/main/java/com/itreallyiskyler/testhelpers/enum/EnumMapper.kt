package com.itreallyiskyler.testhelpers.enum

import com.itreallyiskyler.furblr.enum.IValueAccessor

object EnumMapper {
    inline fun<reified T : Enum<T>> getByValue(searchValue : Any) : T {
        val idMap = enumValues<T>().associateBy { (it as IValueAccessor<*>).id }
        val foundValue = idMap[searchValue]
        if (foundValue == null) {
            throw IndexOutOfBoundsException("What the fuck bro")
        }
        return foundValue!!
    }
    /*inline fun<T, reified U : Enum<U>> map(searchValue : T) : U {
        return enumValues<U>().associateBy { (it as IValueAccessor<*>).id }[searchValue]!!
    }*/
}
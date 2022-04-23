package com.itreallyiskyler.furblr.enum

import java.security.InvalidKeyException

enum class PostGender(val id: Int) {
    Any(0),
    Male(1),
    Female(2),
    Herm(3),
    Intersex(4),
    TransMale(5),
    TransFemale(6),
    NonBinary(7),
    MultipleCharacters(8),
    OtherNotSpecified(9);

    companion object {
        private val mapping : Map<String, PostGender> = mapOf(
            Pair("Any", Any),
            Pair("Male", Male),
            Pair("Female", Female),
            Pair("Herm", Herm),
            Pair("Intersex", Intersex),
            Pair("Trans (Male)", TransMale),
            Pair("Trans (Female)", TransFemale),
            Pair("Non-Binary", NonBinary),
            Pair("Multiple characters", MultipleCharacters),
            Pair("Other / Not Specified", OtherNotSpecified),
        )

        fun fromString(gender : String) : PostGender {
            // TODO : pull these strings from localization so that it stays up to date
            if (!mapping.containsKey(gender)) {
                throw InvalidKeyException(gender + " is not a valid PostGender")
            }
            return mapping.getValue(gender)
        }
    }
}
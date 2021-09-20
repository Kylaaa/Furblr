package com.itreallyiskyler.furblr.enum

import java.security.InvalidKeyException

enum class AgeRating {
    General,
    Mature,
    Adult;

    companion object {
        /*
            Given a string like "r-general" or "r-mature", return the corresponding enum
         */
        fun fromString(className : String) : AgeRating {
            var mapping : Map<String, AgeRating> = mapOf(
                // submission class names
                Pair("r-general", AgeRating.General),
                Pair("r-mature", AgeRating.Mature),
                Pair("r-adult", AgeRating.Adult),

                // attribute names
                Pair("general", AgeRating.General),
                Pair("mature", AgeRating.Mature),
                Pair("adult", AgeRating.Adult),
            );
            if (!mapping.containsKey(className))
            {
                throw InvalidKeyException(className + " is not a valid type of AgeRating")
            }
            return mapping.getValue(className);
        };

        fun fromClassList(classes : Set<String>) : AgeRating {
            if (classes.contains("general")) { return AgeRating.General }
            if (classes.contains("mature")) { return AgeRating.Mature }
            if (classes.contains("adult")) { return AgeRating.Adult }

            throw InvalidKeyException("Cannot find AgeRating in list of class names")
        }
    };
}
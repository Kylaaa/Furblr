package com.itreallyiskyler.furblr.enum

import java.security.InvalidKeyException

enum class PostCategory(val id:Int) {
    // Visual Art
    All(0),
    ArtworkDigital(1),
    ArtworkTraditional(2),
    Cellshading(3),
    Crafting(4),
    Design(5),
    Fursuiting(6),
    Icon(7),
    Mosaics(8),
    Photography(9),
    Food(10),
    Sculpting(11),

    // Readable Art
    Story(12),
    Poetry(13),
    Prose(14),

    // Audio Art
    Music(15),
    Podcasts(16),

    // Downloadables
    Skins(17),
    Handhelds(18),
    Resources(19),

    // Other Stuff
    Adoptables(20),
    Auctions(21),
    Contests(22),
    CurrentEvent(23),
    StockArt(24),
    Screenshots(25),
    Scraps(26),
    Wallpaper(27),
    YCHSale(28),
    Flash(29),
    Other(30);

    companion object {
        private val visualSet : Set<PostCategory> = setOf(
            All, ArtworkDigital, ArtworkTraditional, Cellshading, Crafting, Design, Fursuiting,
            Icon, Mosaics, Photography, Food, Sculpting,

            Adoptables, Auctions, Contests, CurrentEvent, StockArt, Screenshots, Scraps, Wallpaper, YCHSale
        )
        private val readableSet : Set<PostCategory> = setOf(
            Story, Poetry, Prose
        )
        private val audioSet : Set<PostCategory> = setOf(
            Music, Podcasts
        )
        private val downloadableSet : Set<PostCategory> = setOf(
            Skins, Handhelds, Resources
        )

        fun getPostKind(category : PostCategory) : PostKind {
            val categoryMap : MutableMap<PostCategory, PostKind> = mutableMapOf()
            visualSet.forEach       { it -> categoryMap[it] = PostKind.Image }
            readableSet.forEach     { it -> categoryMap[it] = PostKind.Writing }
            audioSet.forEach        { it -> categoryMap[it] = PostKind.Music }
            downloadableSet.forEach { it -> categoryMap[it] = PostKind.Downloadable }
            categoryMap[Flash] = PostKind.Flash

            if (categoryMap.containsKey(category)) { return categoryMap.getValue(category) }

            println("Unhandled category type : " + category.toString())
            return PostKind.Unknown
        }

        /*
            Given a string from the Category field, return a PostCategory
         */
        fun fromString(className : String) : PostCategory {
            // TODO : Pull these strings from localization files to account for other languages
            var mapping : Map<String, PostCategory> = mapOf(
                Pair("All", PostCategory.All),
                Pair("Artwork (Digital)", PostCategory.ArtworkDigital),
                Pair("Artwork (Traditional)", PostCategory.ArtworkTraditional),
                Pair("Cellshading", PostCategory.Cellshading),
                Pair("Crafting", PostCategory.Crafting),
                Pair("Designs", PostCategory.Design),
                Pair("Fursuiting", PostCategory.Fursuiting),
                Pair("Icons", PostCategory.Icon),
                Pair("Mosaics", PostCategory.Mosaics),
                Pair("Photography", PostCategory.Photography),
                Pair("Story", PostCategory.Story),
                Pair("Poetry", PostCategory.Poetry),
                Pair("Prose", PostCategory.Prose),
                Pair("Music", PostCategory.Music),
                Pair("Podcasts", PostCategory.Podcasts),
                Pair("Skins", PostCategory.Skins),
                Pair("Handhelds", PostCategory.Handhelds),
                Pair("Resources", PostCategory.Resources),
                Pair("Adoptables", PostCategory.Adoptables),
                Pair("Auctions", PostCategory.Auctions),
                Pair("Contests", PostCategory.Contests),
                Pair("Current Events", PostCategory.CurrentEvent),
                Pair("Stockart", PostCategory.StockArt),
                Pair("Screenshots", PostCategory.Screenshots),
                Pair("Scraps", PostCategory.Scraps),
                Pair("Wallpaper", PostCategory.Wallpaper),
                Pair("YCH / Sale", PostCategory.YCHSale),
                Pair("Flash", PostCategory.Flash),
                Pair("Other", PostCategory.Other),
            )
            if (!mapping.containsKey(className))
            {
                throw InvalidKeyException(className + " is not a valid type of PostCategory")
            }
            return mapping.getValue(className);

        }
    }
}
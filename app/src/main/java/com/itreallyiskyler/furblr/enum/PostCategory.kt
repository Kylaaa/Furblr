package com.itreallyiskyler.furblr.enum

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
    Other(29);

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

            if (categoryMap.containsKey(category)) { return categoryMap.getValue(category) }

            println("Unhandled category type : " + category.toString())
            return PostKind.Unknown
        }
    }
}
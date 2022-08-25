package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.*
import com.itreallyiskyler.furblr.util.DateFormatter
import com.itreallyiskyler.testhelpers.util.ResourceFetcher
import org.junit.Assert.*
import org.junit.Test

class PagePostDetailsUnitTest {

    @Test
    fun constructor_parsesAudioPost() {
        val EXAMPLE_BODY_AUDIO : String = ResourceFetcher.ReadTextFromResource("exampleView_Audio.html")
        val details = PagePostDetails.parseFromHttp(EXAMPLE_BODY_AUDIO)

        assertEquals(details.artist, "LameCheez")
        assertEquals(details.comments.size, 0)
        assertEquals(details.contentUrl, "https://d.furaffinity.net/art/lamecheez/music/1649367062/1649367062.lamecheez_информатика.mp3")
        assertEquals(details.description.substring(0, 33), "HEWWOOO!!! My name is Lame Cheez.")
        assertEquals(details.favoriteKey, "b3d836ea3258f7c05baeafa70a8ecd6aea69406b")
        assertEquals(details.hasFavorited, false)
        assertEquals(details.kind, PostKind.Music)
        assertEquals(details.rating, AgeRating.General)
        assertEquals(details.tags.size, 8)
        assertEquals(details.thumbnailUrl, "https://d.furaffinity.net/art/lamecheez/music/1649367062/1649367062.thumbnail.lamecheez_информатика.mp3.jpg")
        assertEquals(details.title, "AVISHKA PARADISE")
        assertEquals(details.totalFavorites, 0)
        assertEquals(details.totalViews, 69)
        assertEquals(details.uploadDate, DateFormatter.createDate(2022, 4, 7, 17, 31))
    }

    @Test
    fun constructor_parsesImagePost() {
        val EXAMPLE_BODY_IMAGE : String = ResourceFetcher.ReadTextFromResource("exampleView_Image.html")
        val details : PagePostDetails = PagePostDetails.parseFromHttp(EXAMPLE_BODY_IMAGE)

        assertEquals(details.artist, "nimfaeya")
        assertEquals(details.comments.size, 0)
        assertEquals(details.contentUrl, null)
        //assertEquals(details.Description, "")
        assertEquals(details.favoriteKey, "263aecbeb29aed6793c16f7748f9f07a89a6bda1")
        assertEquals(details.hasFavorited, false)
        assertEquals(details.kind, PostKind.Image)
        assertEquals(details.rating, AgeRating.General)
        assertEquals(details.tags.size, 6)
        assertEquals(details.thumbnailUrl, "https://d.furaffinity.net/art/nimfaeya/1654462809/1654462809.nimfaeya_2016-07-26.png")
        assertEquals(details.title, "The Black Tea Geisha VII")
        assertEquals(details.totalFavorites, 0)
        assertEquals(details.totalViews, 8)
        assertEquals(details.uploadDate, DateFormatter.createDate(2022, 6, 5, 17, 0))
    }

    @Test
    fun constructor_parsesImagePost_Favorited() {
        val EXAMPLE_BODY_IMAGE : String = ResourceFetcher.ReadTextFromResource("exampleView_ImageFavorited.html")
        val details : PagePostDetails = PagePostDetails.parseFromHttp(EXAMPLE_BODY_IMAGE)

        assertEquals(details.artist, "Plive")
        assertEquals(details.comments.size, 2)
        assertEquals(details.contentUrl, null)
        //assertEquals(details.description, "")
        assertEquals(details.favoriteKey, "e8b2f4c18b2d345a75031ed10399fca1eb9b615b")
        assertEquals(details.hasFavorited, true)
        assertEquals(details.kind, PostKind.Image)
        assertEquals(details.rating, AgeRating.General)
        assertEquals(details.tags.size, 6)
        assertEquals(details.thumbnailUrl, "https://d.furaffinity.net/art/plive/1650701544/1650701544.plive_snekk.jpg")
        assertEquals(details.title, "Serpejar")
        assertEquals(details.totalFavorites, 36)
        assertEquals(details.totalViews, 261)
        assertEquals(details.uploadDate, DateFormatter.createDate(2022, 4, 23, 4, 12))
    }
}

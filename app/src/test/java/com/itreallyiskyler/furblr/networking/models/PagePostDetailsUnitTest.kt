package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.*
import com.itreallyiskyler.furblr.helpers.ResourceFetcher
import com.itreallyiskyler.furblr.util.DateFormatter
import org.junit.Assert.*
import org.junit.Test

class PagePostDetailsUnitTest {

    @Test
    fun constructor_parsesAudioPost() {
        val EXAMPLE_BODY_AUDIO : String = ResourceFetcher.ReadTextFromResource("exampleView_Audio.html")
        val details = PagePostDetails(EXAMPLE_BODY_AUDIO)

        assertEquals(details.Artist, "LameCheez")
        assertEquals(details.Comments.size, 0)
        assertEquals(details.ContentUrl, "https://d.furaffinity.net/art/lamecheez/music/1649367062/1649367062.lamecheez_информатика.mp3")
        assertEquals(details.Description.substring(0, 33), "HEWWOOO!!! My name is Lame Cheez.")
        assertEquals(details.FavoriteKey, "b3d836ea3258f7c05baeafa70a8ecd6aea69406b")
        assertEquals(details.HasFavorited, false)
        assertEquals(details.Kind, PostKind.Music)
        assertEquals(details.Rating, AgeRating.General)
        assertEquals(details.Tags.size, 8)
        assertEquals(details.ThumbnailUrl, "https://d.furaffinity.net/art/lamecheez/music/1649367062/1649367062.thumbnail.lamecheez_информатика.mp3.jpg")
        assertEquals(details.Title, "AVISHKA PARADISE")
        assertEquals(details.TotalFavorites, 0)
        assertEquals(details.TotalViews, 69)
        assertEquals(details.UploadDate, DateFormatter.createDate(2022, 4, 7, 17, 31))
    }

    @Test
    fun constructor_parsesImagePost() {
        val EXAMPLE_BODY_IMAGE : String = ResourceFetcher.ReadTextFromResource("exampleView_Image.html")
        var details : PagePostDetails = PagePostDetails(EXAMPLE_BODY_IMAGE)

        assertEquals(details.Artist, "nimfaeya")
        assertEquals(details.Comments.size, 0)
        assertEquals(details.ContentUrl, null)
        //assertEquals(details.Description, "")
        assertEquals(details.FavoriteKey, "263aecbeb29aed6793c16f7748f9f07a89a6bda1")
        assertEquals(details.HasFavorited, false)
        assertEquals(details.Kind, PostKind.Image)
        assertEquals(details.Rating, AgeRating.General)
        assertEquals(details.Tags.size, 6)
        assertEquals(details.ThumbnailUrl, "https://d.furaffinity.net/art/nimfaeya/1654462809/1654462809.nimfaeya_2016-07-26.png")
        assertEquals(details.Title, "The Black Tea Geisha VII")
        assertEquals(details.TotalFavorites, 0)
        assertEquals(details.TotalViews, 8)
        assertEquals(details.UploadDate, DateFormatter.createDate(2022, 6, 5, 17, 0))
    }
}

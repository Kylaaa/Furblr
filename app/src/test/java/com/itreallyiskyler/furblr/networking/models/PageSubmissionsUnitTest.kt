package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.AgeRating
import com.itreallyiskyler.testhelpers.mocks.MockThumbnail
import com.itreallyiskyler.testhelpers.util.ResourceFetcher
import org.junit.Assert.*
import org.junit.Test
import java.lang.AssertionError

class PageSubmissionsUnitTest {
    fun matchesThumbnailData(a : IThumbnail, b: IThumbnail) {
        try {
            assertEquals("Age Rating", a.ageRating, b.ageRating)
            assertEquals("Creator Name", a.creatorName, b.creatorName)
            assertEquals("Image Height", a.imageHeight, b.imageHeight)
            assertEquals("Image Width", a.imageWidth, b.imageWidth)
            assertEquals("Post Id", a.postId, b.postId)
            assertEquals("Title", a.title, b.title)
        }
        catch (ex : AssertionError) {
            fail("${b.title} by ${b.creatorName} does not match the parsed version : ${ex.message}")
        }
    }

    @Test
    fun constructor_parsesPage() {
        val EXAMPLE_BODY : String = ResourceFetcher.ReadTextFromResource("exampleSubmissions.html")
        val submissions = PageSubmissions.parseFromHttp(EXAMPLE_BODY)

        val expectedThumbnails : Array<MockThumbnail> = arrayOf(
            MockThumbnail(48030294, AgeRating.General, "https://t.furaffinity.net/48030294@200-1657469660.jpg", 154.219f, 200.0f, "Commission", "yec_yourz"),
            MockThumbnail(48030156, AgeRating.Mature, "https://t.furaffinity.net/48030156@300-1657468888.jpg", 300.0f, 200.0f, "evil_itself", "Miles-DF"),
            MockThumbnail(48030044, AgeRating.Adult, "https://t.furaffinity.net/48030044@400-1657468368.jpg", 338.369f, 200.0f, "Peppers", "PumpkinCreampie"),
            MockThumbnail(48030033, AgeRating.Adult, "https://t.furaffinity.net/48030033@200-1657468319.jpg", 191.35f,  200.0f, "Peppers", "PumpkinCreampie"),
            MockThumbnail(48030015, AgeRating.Adult, "https://t.furaffinity.net/48030015@400-1657468259.jpg", 355.556f, 200.0f, "Horny anthro YCH", "AnChee"),
            MockThumbnail(48029907, AgeRating.Adult, "https://t.furaffinity.net/48029907@300-1657467618.jpg", 291.572f, 200.0f, "Playful boy!", "AnChee"),
            MockThumbnail(48029555, AgeRating.General, "https://t.furaffinity.net/48029555@300-1657465587.jpg", 226.148f, 200.0f, "Sigurd biker", "Kardie"),
            MockThumbnail(48027510, AgeRating.Mature, "https://t.furaffinity.net/48027510@300-1657450124.jpg", 231.884f, 200.0f, "wyd?", "Schmutzo"),
            MockThumbnail(48027182, AgeRating.General, "https://t.furaffinity.net/48027182@400-1657446478.jpg", 400.0f, 181.333f, "Commissions reminder", "fangsboi"),
            MockThumbnail(48027051, AgeRating.Adult, "https://t.furaffinity.net/48027051@300-1657445286.jpg", 224.365f, 200.0f, "Raffle result", "Dimonis"),
        )

        for (i in expectedThumbnails.indices) {
            matchesThumbnailData(submissions.submissions[i], expectedThumbnails[i])
        }

        assertEquals(submissions.submissions.size, 48)
    }
}

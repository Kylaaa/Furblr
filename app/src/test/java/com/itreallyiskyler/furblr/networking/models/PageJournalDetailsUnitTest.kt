package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.testhelpers.util.ResourceFetcher
import org.junit.Assert.*
import org.junit.Test

class PageJournalDetailsUnitTest {
    @Test
    fun constructor_parsesPage() {
        val EXAMPLE_BODY : String = ResourceFetcher.ReadTextFromResource("exampleJournal.html")
        val journal = PageJournalDetails.parseFromHttp(EXAMPLE_BODY)

        assertEquals(journal.artist, "LynxWolf")
        assertEquals(journal.comments.size, 3)
        assertEquals(journal.title, "Out of town for 4 days.")
    }
}

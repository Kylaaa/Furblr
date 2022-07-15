package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.helpers.ResourceFetcher
import org.junit.Assert.*
import org.junit.Test

class PageJournalDetailsUnitTest {
    @Test
    fun constructor_parsesPage() {
        val EXAMPLE_BODY : String = ResourceFetcher.ReadTextFromResource("exampleJournal.html")
        val journal = PageJournalDetails(EXAMPLE_BODY)

        assertEquals(journal.Artist, "LynxWolf")
        assertEquals(journal.Comments.size, 3)
        assertEquals(journal.Title, "Out of town for 4 days.")
    }
}

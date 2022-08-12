package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.testhelpers.util.ResourceFetcher
import org.junit.Assert.*
import org.junit.Test

class PageUserDetailsUnitTest {
    @Test
    fun constructor_parsesPage() {
        val EXAMPLE_BODY : String = ResourceFetcher.ReadTextFromResource("exampleUser.html")
        val user = PageUserDetails("blitzdrachin", EXAMPLE_BODY)

        assertEquals(user.username, "blitzdrachin")
        assertEquals(user.avatarId, 1645982594)
    }
}

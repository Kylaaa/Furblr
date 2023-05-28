package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.util.DateFormatter
import com.itreallyiskyler.testhelpers.util.ResourceFetcher
import org.junit.Assert.*
import org.junit.Test

class PageUserDetailsUnitTest {
    @Test
    fun constructor_parsesPage() {
        val EXAMPLE_BODY : String = ResourceFetcher.ReadTextFromResource("exampleUser.html")
        val user = PageUserDetails.parseFromHttp(EXAMPLE_BODY)

        assertEquals(user.username, "BubbleWolf")
        assertEquals(user.avatarId, 1514822756)
        //assertEquals(user.dateJoined, DateFormatter.createDate(2009, 5, 18, 9, 51))
    }
}

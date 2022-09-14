package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.testhelpers.util.ResourceFetcher
import org.jsoup.Jsoup
import org.junit.Assert
import org.junit.Test

class PostCommentUnitTest {
    @Test
    fun constructor_parsesHiddenComment() {
        val example = ResourceFetcher.ReadTextFromResource("exampleComment_Hidden.html")
        val body = Jsoup.parse(example)
        val element = body.body().child(0)!!
        val comment = PostComment.parseFromElement(element)

        Assert.assertEquals(comment, null)
    }

}
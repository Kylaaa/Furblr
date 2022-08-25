package com.itreallyiskyler.furblr.networking.models

import org.jsoup.nodes.Element

/*
    Example :
    <span class="tags"><a href="/search/@keywords Male">Male</a></span>
 */

data class PostTag(
    override val content: String
) : IPostTag {

    companion object : IParserElement<PostTag> {
        override fun parseFromElement(container: Element): PostTag {
            val content: String = container.children()[0].text()
            return PostTag(content)
        }

        fun Compare(a : PostTag?, b : PostTag?) : Int {
            if (a == null && b == null)
                return 0

            if (a == null)
                return -1

            if (b == null)
                return 1

            return a.content.compareTo(b.content)
        }
    }
}

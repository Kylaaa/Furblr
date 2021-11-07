package com.itreallyiskyler.furblr.networking.models

import org.jsoup.nodes.Element

/*
    Example :
    <span class="tags"><a href="/search/@keywords Male">Male</a></span>
 */

class PostTag(elementData: Element) : IPostTag {
    override var Content : String = elementData.children()[0].text()

    companion object {
        fun Compare(a : PostTag?, b : PostTag?) : Int {
            if (a == null && b == null)
                return 0

            if (a == null)
                return -1

            if (b == null)
                return 1

            return a.Content.compareTo(b.Content)
        }
    }
}

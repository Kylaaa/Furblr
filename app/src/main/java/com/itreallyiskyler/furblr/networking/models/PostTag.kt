package com.itreallyiskyler.furblr.networking.models

import org.jsoup.nodes.Element

/*
    Example :
    <span class="tags"><a href="/search/@keywords Male">Male</a></span>
 */

class PostTag(elementData: Element) : IPostTag {
    override var Content : String = elementData.children()[0].text()
}

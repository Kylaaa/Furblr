package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.util.DateFormatter
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/*
    Example :

    <div class="comment_container" data-timestamp="1624691741" style="width:100%">
        <a id="cid:157106970" class="comment_anchor"></a>
        <div class="avatar-desktop">
            <a href="/user/sutherncross2006/"><img class="comment_useravatar" src="//a.furaffinity.net/1620298040/sutherncross2006.gif" alt="sutherncross2006"></a>
        </div>
        <div class="base">
            <div class="header">
                <div class="name class">
                    <div class="table">
                        <div class="cell avatar-mobile">
                            <a href="/user/sutherncross2006/"><img class="comment_useravatar" src="" alt="sutherncross2006"></a>
                        </div>
                        <div class="cell">
                            <a class="inline" href="/user/sutherncross2006/"><strong class="comment_username"><h3>sutherncross2006</h3></strong></a>
                            <span class="hideonmobile font-small">Watcher</span>
                            <div class="font-small floatright">
                                <a class="comment-link" href="#cid:157106970" title="Link to this Comment">#link</a>
                            </div>
                            <div class="comment-date font-small">
                                <span title="Jun 26, 2021 03:15 AM" class="popup_date">3 months ago</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="body comment_text user-submitted-links">
                Dang &lt;3
            </div>
            <div class="footer">
                <a class="replyto_link" href="/replyto/submission/157106970/">Reply</a>
            </div>
        </div>
    </div>
 */

// HIDDEN COMMENTS
/*
    <div class="comment_container collapsed_height" style="width:94%">
        <a id="cid:159349538" class="comment_anchor"></a>
        <div class="avatar avatar-desktop">
            &nbsp;
        </div>
        <div class="base">
            <div class="body">
                <strong>Comment hidden by its owner</strong>
            </div>
        </div>
    </div>
 */

data class PostComment(
    override val id : Long,
    override val content : String,
    override val uploaderAvatar : String,
    override val uploaderName : String,
    override val uploaderTitle : String,
    override val date : String
) : IPostComment {

    companion object : IParserElement<PostComment?> {
        // TODO Figure out how to fail to parse an object
        override fun parseFromElement(container: Element): PostComment? {
            // TODO : detect hidden comments
            // TODO : detect parent / child relationship to comments
            val idString: String = container.child(0).id()
            val id: Long = idString.split(":")[1].toLong()
            val contentContainer: Elements = container.getElementsByClass("comment_text")
            if (contentContainer.size == 0) {
                return null
                //throw IndexOutOfBoundsException("Couldn't parse the comment because it's hidden")
            }
            val content : String = contentContainer[0]?.text() ?: ""

            val avatarImages = container.getElementsByClass("comment_useravatar")
            if (avatarImages.size == 0) {
                return null
            }
            val uploaderAvatar: String = avatarImages[0].attr("src")

            val usernameElement = container.getElementsByClass("comment_username")[0]
            val uploaderName: String = usernameElement.text()
            val uploaderTitle: String = container.getElementsByTag("comment-title")[0].text()

            val dateText: String = container.getElementsByTag("comment-date")[0].child(0).attr("title")
            val df = DateFormatter(dateText)
            val date = df.toYYYYMMDDhhmm()

            return PostComment(id, content, uploaderAvatar, uploaderName, uploaderTitle, date)
        }
    }
}

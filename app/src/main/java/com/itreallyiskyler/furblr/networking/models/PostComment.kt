package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.util.DateFormatter
import org.jsoup.nodes.Element

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

class PostComment(elementData: Element) : IPostComment {
    // TODO : detect hidden comments

    private val idString : String = elementData.child(0).id()
    override var Id : Long = idString.split(":")[1].toLong()
    override var Content : String = elementData.getElementsByClass("comment_text")[0].text()

    private val avatarImage = elementData.getElementsByClass("comment_useravatar")[0]
    override var UploaderAvatar : String = avatarImage.attr("src")

    private var cells = elementData.getElementsByClass("cell")
    private val userElement = cells[1]
    private val usernameElement = userElement.getElementsByClass("comment_username")[0]
    override var UploaderName : String = usernameElement.child(0).text()
    override var UploaderTitle : String = userElement.getElementsByClass("hideonmobile")[0].text()

    private val dateText : String = userElement.getElementsByClass("popup_date")[0].attr("title")
    private val df = DateFormatter(dateText)
    override var Date = df.toYYYYMMDDhhmm()
}

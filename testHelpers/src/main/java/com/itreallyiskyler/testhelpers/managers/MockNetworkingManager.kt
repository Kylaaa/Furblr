package com.itreallyiskyler.testhelpers.managers

import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.enum.SubmissionScrollDirection
import com.itreallyiskyler.furblr.managers.INetworkingManager
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.networking.requests.IRequestAction
import com.itreallyiskyler.furblr.networking.requests.IUrlFetcher
import com.itreallyiskyler.furblr.networking.requests.RequestHandler
import com.itreallyiskyler.furblr.util.GenericCallback
import com.itreallyiskyler.furblr.util.LoggingChannel
import okhttp3.Request
import java.net.URL

open class MockNetworkingManager : INetworkingManager {
    override val logChannel: LoggingChannel = LoggingChannel("NetworkingManagerStub", LogLevel.NONE)
    override val requestHandler: RequestHandler = { url : URL, _ : Request, _ : GenericCallback, _ : GenericCallback ->
        throw Exception("Unimplemented request to $url")
    }

    override fun requestAvatarUrl(username: String, avatarId: Long): IUrlFetcher {
        throw Exception("Unimplemented avatar url request for $username")
    }

    override fun requestCommentPost(postId: Long, message: String): IRequestAction {
        throw Exception("Unimplemented request to comment on $postId")
    }

    override fun requestHome(): IRequestAction {
        throw Exception("Unimplemented request to fetch home")
    }

    override fun requestFavoritePost(postId: Long, favoriteKey: String): IRequestAction {
        throw Exception("Unimplemented request")
    }

    override fun requestJournalDetails(journalId: Long): IRequestAction {
        throw Exception("Unimplemented request")
    }

    override fun requestLogin(): IUrlFetcher {
        throw Exception("Unimplemented url")
    }

    override fun requestNotifications(): IRequestAction {
        throw Exception("Unimplemented request")
    }

    override fun requestSearch(
        keyword: String,
        searchOptions: SearchOptions
    ): IRequestAction {
        throw Exception("Unimplemented request")
    }

    override fun requestSubmissions(
        scrollDirection: SubmissionScrollDirection,
        pageSize: Int,
        offsetId: Long?
    ): IRequestAction {
        throw Exception("Unimplemented request")
    }

    override fun requestUnfavoritePost(postId: Long, favoriteKey: String): IRequestAction {
        throw Exception("Unimplemented request")
    }

    override fun requestUser(userId: String): IRequestAction {
        throw Exception("Unimplemented request")
    }

    override fun requestView(postId: Long): IRequestAction {
        throw Exception("Unimplemented request")
    }
}
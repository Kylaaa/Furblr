package com.itreallyiskyler.furblr.managers

import com.itreallyiskyler.furblr.enum.SubmissionScrollDirection
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.networking.requests.IRequestAction
import com.itreallyiskyler.furblr.networking.requests.IUrlFetcher
import com.itreallyiskyler.furblr.networking.requests.RequestHandler
import com.itreallyiskyler.furblr.util.LoggingChannel

interface INetworkingManager {
    val requestHandler : RequestHandler
    val logChannel : LoggingChannel


    fun requestAvatarUrl(username : String, avatarId : Long) : IUrlFetcher

    fun requestCommentPost(postId : Long, message : String) : IRequestAction

    fun requestFavoritePost(postId : Long, favoriteKey: String) : IRequestAction

    fun requestHome() : IRequestAction

    fun requestJournalDetails(journalId : Long) : IRequestAction

    fun requestLogin() : IUrlFetcher

    fun requestNotifications() : IRequestAction

    fun requestSearch(keyword : String = "", searchOptions : SearchOptions = SearchOptions()) : IRequestAction

    fun requestSubmissions(
        scrollDirection: SubmissionScrollDirection,
        pageSize: Int,
        offsetId: Long?) : IRequestAction

    fun requestUnfavoritePost(postId : Long, favoriteKey: String) : IRequestAction

    fun requestUser(userId : String) : IRequestAction

    fun requestView(postId : Long) : IRequestAction
}
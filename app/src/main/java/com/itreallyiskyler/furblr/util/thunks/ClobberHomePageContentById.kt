package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.BlacklistedTag
import com.itreallyiskyler.furblr.ui.home.HomePagePost
import com.itreallyiskyler.furblr.util.GenericCallback
import com.itreallyiskyler.furblr.util.Promise

fun ClobberHomePageContentById(dbImpl : AppDatabase,
                         assetIds : List<Long>) : List<HomePagePost> {
    val homeDao = dbImpl.homePageDao()
    val postsDao = dbImpl.postsDao()

    val blacklistedTags : Set<String> = homeDao.getBlacklistedTags().map {
        tag: BlacklistedTag -> tag.tagContents
    }.toSet()

    val posts = postsDao.getExistingPostsWithIds(assetIds)
    val postIds = posts.map { post -> post.id }
    val tags = homeDao.getTagsForPosts(postIds)
    val comments = homeDao.getCommentsForPosts(postIds)

    // clobber together the data
    val homePagePosts: MutableList<HomePagePost> = mutableListOf()
    posts.forEach { post ->
        run {
            val postTags = tags.filter { tag -> tag.parentPostId == post.id }
            val postComments = comments.filter { comment -> comment.postId == post.id }
            val hpp = HomePagePost(post, postTags, postComments)

            // filter out any blacklisted content
            var isBlacklisted = false
            postTags.forEach { tag ->
                isBlacklisted = isBlacklisted || blacklistedTags.contains(tag.tagContents)
            }
            if (!isBlacklisted) {
                homePagePosts.add(hpp)
            }
        }
    }

    return homePagePosts.toList()
}
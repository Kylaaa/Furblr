package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.CommentLocationId
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost

fun ClobberHomePageImagesById(assetIds : List<Long>) : List<HomePageImagePost> {
    val dbImpl = SingletonManager.get().DBManager.getDB()
    val commentsDao = dbImpl.commentsDao()
    val tagsDao = dbImpl.tagsDao()
    val viewsDao = dbImpl.viewsDao()
    val usersDao = dbImpl.usersDao()

    // remove any assetIds that contain blacklisted content
    var filteredIds = assetIds
    val blacklistedTags = dbImpl.blacklistedTagsDao().getAllBlacklistedTags()
    if (blacklistedTags.size > 0) {
        filteredIds = dbImpl.blacklistedTagsDao().getUnblacklistedIdsForPosts(assetIds)
    }

    val views = viewsDao.getExistingViewsWithIds(filteredIds)
    val postIds = views.map { post -> post.id }
    val creatorIds = views.map { post -> post.profileId }
    val tags = tagsDao.getTagsForPosts(postIds)
    val comments = commentsDao.getCommentsForPosts(postIds, CommentLocationId.Post.id)
    val users = usersDao.getExistingUsersForUsernames(creatorIds)

    // clobber together the data
    val homePageImagePosts: MutableList<HomePageImagePost> = mutableListOf()
    views.forEach { post ->
        run {
            val postCreator = users.find { user -> user.username == post.profileId }!!
            val postTags = tags.filter { tag -> tag.parentPostId == post.id }
            val postComments = comments.filter { comment -> comment.hostId == post.id }

            val hpp = HomePageImagePost(post, postCreator, postTags, postComments)
            homePageImagePosts.add(hpp)
        }
    }

    return homePageImagePosts.sortedByDescending { it.postData.date }.toList()
}
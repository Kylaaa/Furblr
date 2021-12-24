package com.itreallyiskyler.furblr.persistence.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itreallyiskyler.furblr.persistence.dao.*
import com.itreallyiskyler.furblr.persistence.entities.*

// TODO : export the schema to track the version history of the db
@Database(version = 1,
    entities = [BlacklistedTag::class,
        Comment::class,
        FeedId::class,
        Journal::class,
        Post::class,
        Shout::class,
        Tag::class,
        User::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // content actions...
    abstract fun homePageDao() : HomePageDao
    abstract fun profilePageDao() : ProfilePageDao

    // insert and update actions...
    abstract fun contentFeedDao() : ContentFeedDao
    abstract fun blacklistedTagsDao() : BlacklistedTagsDao
    abstract fun commentsDao() : CommentsDao
    abstract fun journalsDao() : JournalsDao
    abstract fun postsDao() : PostsDao
    abstract fun shoutsDao() : ShoutsDao
    abstract fun tagsDao() : TagsDao
    abstract fun usersDao() : UsersDao

    // debug queries
    abstract fun debuggingDao() : DebuggingDao
}

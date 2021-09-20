package com.itreallyiskyler.furblr.persistence.db

import android.os.Debug
import androidx.room.Database
import androidx.room.RoomDatabase
import com.itreallyiskyler.furblr.persistence.dao.*
import com.itreallyiskyler.furblr.persistence.entities.*

@Database(version = 1,
    entities = arrayOf(
        BlacklistedTag::class,
        Comment::class,
        Post::class,
        Shout::class,
        Tag::class,
        User::class))
abstract class AppDatabase : RoomDatabase() {
    // content actions...
    abstract fun homePageDao() : HomePageDao
    abstract fun profilePageDao() : ProfilePageDao

    // insert and update actions...
    abstract fun blacklistedTagsDao() : BlacklistedTagsDao
    abstract fun commentsDao() : CommentsDao
    abstract fun postsDao() : PostsDao
    abstract fun shoutsDao() : ShoutsDao
    abstract fun tagsDao() : TagsDao
    abstract fun usersDao() : UsersDao

    // debug queries
    abstract fun debuggingDao() : DebuggingDao
}
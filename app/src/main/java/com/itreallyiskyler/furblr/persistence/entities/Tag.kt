package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.*

const val TAGS_TABLE_NAME = "tag"
const val TAGS_COLUMN_NAME_POST_ID = "postId"
const val TAGS_COLUMN_NAME_CONTENTS = "tagContents"

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(entity = Post::class,
            parentColumns = arrayOf("$POSTS_COLUMN_NAME_ID"),
            childColumns = arrayOf("$TAGS_COLUMN_NAME_POST_ID"),
            onDelete = ForeignKey.CASCADE
        )
    ),
    indices = arrayOf(
        Index(TAGS_COLUMN_NAME_POST_ID)
    )
)
data class Tag(
    var parentPost : Long,
    var contents : String) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "$TAGS_COLUMN_NAME_POST_ID")
    var parentPostId: Long = parentPost

    @ColumnInfo(name = "$TAGS_COLUMN_NAME_CONTENTS")
    var tagContents: String = contents
}
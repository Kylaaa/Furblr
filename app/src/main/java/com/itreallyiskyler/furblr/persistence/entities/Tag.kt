package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.*

const val TAGS_TABLE_NAME = "tag"
const val TAGS_COLUMN_NAME_POST_ID = "postId"
const val TAGS_COLUMN_NAME_CONTENTS = "tagContents"

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(entity = View::class,
            parentColumns = arrayOf("$VIEW_COLUMN_NAME_ID"),
            childColumns = arrayOf("$TAGS_COLUMN_NAME_POST_ID"),
            onDelete = ForeignKey.CASCADE
        )
    ),
    indices = arrayOf(
        Index(TAGS_COLUMN_NAME_POST_ID)
    )
)
data class Tag(
    val _parentPost : Long,
    val _contents : String) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "$TAGS_COLUMN_NAME_POST_ID")
    var parentPostId: Long = _parentPost

    @ColumnInfo(name = "$TAGS_COLUMN_NAME_CONTENTS")
    var tagContents: String = _contents.lowercase()
}
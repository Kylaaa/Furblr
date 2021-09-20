package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

const val BLACKLISTED_TAGS_TABLE_NAME = "blacklistedtag"
const val BLACKLISTED_TAGS_COLUMN_NAME_CONTENTS = "tagContents"

@Entity
data class BlacklistedTag(var contents : String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "$TAGS_COLUMN_NAME_CONTENTS")
    var tagContents: String = contents
}
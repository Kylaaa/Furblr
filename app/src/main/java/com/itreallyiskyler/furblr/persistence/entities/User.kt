package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// A User includes the details from a specific user's profile
// - profile Shouts exist in a separate entity
// - user Comments exist in a separate entity

const val USERS_TABLE_NAME = "user"
const val USERS_COLUMN_NAME_ID = "username"
const val USERS_COLUMN_NAME_SPECIES = "species"
const val USERS_COLUMN_NAME_DESCRIPTION = "description"
const val USERS_COLUMN_NAME_DATE_JOINED = "dateJoined"

@Entity
data class User(
    @PrimaryKey @ColumnInfo(name = "$USERS_COLUMN_NAME_ID") var username: String,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_SPECIES") var species : String?,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_DESCRIPTION") var description : String,
    @ColumnInfo(name = "$USERS_COLUMN_NAME_DATE_JOINED") var dateJoined : String,
)

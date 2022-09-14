package com.itreallyiskyler.furblr.networking.models


interface IPostComment {
    val id : Long
    val content : String
    val uploaderAvatar : String
    val uploaderName : String
    val uploaderTitle : String
    val date : String
}

package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.EntitiesTestClass
import com.itreallyiskyler.furblr.persistence.entities.*

class UserUnitTest : EntitiesTestClass(User::class.java, USERS_TABLE_NAME) {
}
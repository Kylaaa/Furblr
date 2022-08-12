package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.entities.*
import com.itreallyiskyler.testhelpers.persistence.EntitiesTestClass

class UserUnitTest : EntitiesTestClass(User::class.java, USERS_TABLE_NAME) {
}
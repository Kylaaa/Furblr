package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.entities.*
import com.itreallyiskyler.testhelpers.persistence.EntitiesTestClass

class BlacklistedTagUnitTest : EntitiesTestClass(BlacklistedTag::class.java, BLACKLISTED_TAGS_TABLE_NAME) {
}
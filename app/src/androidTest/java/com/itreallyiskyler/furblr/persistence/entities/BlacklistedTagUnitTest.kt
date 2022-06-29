package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.EntitiesTestClass
import com.itreallyiskyler.furblr.persistence.entities.*

class BlacklistedTagUnitTest : EntitiesTestClass(BlacklistedTag::class.java, BLACKLISTED_TAGS_TABLE_NAME) {
}
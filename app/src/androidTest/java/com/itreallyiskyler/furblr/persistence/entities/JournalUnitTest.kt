package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.EntitiesTestClass
import com.itreallyiskyler.furblr.persistence.entities.*

class JournalUnitTest : EntitiesTestClass(Journal::class.java, JOURNALS_TABLE_NAME){
}
package com.itreallyiskyler.furblr

import com.itreallyiskyler.furblr.persistence.entities.*
import com.itreallyiskyler.testhelpers.persistence.EntitiesTestClass

class JournalUnitTest : EntitiesTestClass(Journal::class.java, JOURNALS_TABLE_NAME){
}
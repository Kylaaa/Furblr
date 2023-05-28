package com.itreallyiskyler.testhelpers.persistence

import org.junit.Assert
import org.junit.Test

abstract class EntitiesTestClass(private val entityClass: Class<*>, private val expectedName : String) {

    @Test
    fun tableNameMatchesEntity() {
        Assert.assertEquals(entityClass.simpleName.lowercase(), expectedName)
    }
}
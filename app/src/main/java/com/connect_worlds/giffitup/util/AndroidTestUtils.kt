package com.connect_worlds.giffitup.util

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidTestUtils
@Inject
constructor(
    private val isTest: Boolean
) {
    fun isTest() = isTest
}
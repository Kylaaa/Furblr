package com.itreallyiskyler.testhelpers.managers

import com.itreallyiskyler.furblr.managers.*

object SingletonInitializer {
    fun init(
        authImpl : IAuthManager = MockAuthManager(),
        contentImpl : IContentManager = MockContentManager(),
        databaseImpl : IDBManager = MockDBManager(),
        loggingManager: ILoggingManager = MockLoggingManager(),
        networkingManager: INetworkingManager = MockNetworkingManager()
    ) {
        SingletonManager.init(
            am = authImpl,
            cm = contentImpl,
            dbm = databaseImpl,
            lm = loggingManager,
            nm = networkingManager,
        )
    }
}
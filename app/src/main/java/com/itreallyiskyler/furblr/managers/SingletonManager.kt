package com.itreallyiskyler.furblr.managers

class SingletonManager(
    val AuthManager : IAuthManager,
    val ContentManager : IContentManager,
    val DBManager : IDBManager,
    val LoggingManager : ILoggingManager,
    val NetworkingManager : INetworkingManager)
{

    companion object : IManagerAccessor<SingletonManager> {
        private lateinit var instance : SingletonManager
        override fun get() : SingletonManager {
            return instance
        }

        fun init(
            am : IAuthManager,
            cm : IContentManager,
            dbm : IDBManager,
            lm : ILoggingManager,
            nm : INetworkingManager) {
            instance = SingletonManager(am, cm, dbm, lm, nm)
        }
    }
}
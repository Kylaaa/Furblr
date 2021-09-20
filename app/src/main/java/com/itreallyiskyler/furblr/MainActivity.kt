package com.itreallyiskyler.furblr

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.itreallyiskyler.furblr.databinding.ActivityMainBinding
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.util.AuthManager
import com.itreallyiskyler.furblr.util.Command
import com.itreallyiskyler.furblr.util.CommandWithArgs1
import com.itreallyiskyler.furblr.util.ContentManager

// TODO : Fetch different pages
// TODO : Scrape image URLs from page source
// TODO : Download images to local storage
// TODO : Create infinite scrolling view of paged results


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    private var connections : ArrayList<Command<Unit>> = arrayListOf();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contentDB = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "furblr-db"
        ).build()
        contentDB.run {
            clearAllTables()
        }
        ContentManager.setDB(contentDB)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val tabNavigationIds = setOf(
            R.id.navigation_home,
            R.id.navigation_discover,
            R.id.navigation_notifications,
            R.id.navigation_marketplace)
        val appBarConfiguration = AppBarConfiguration(tabNavigationIds)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
    }

    private fun fetchContent() {
        ContentManager.fetchSubmissions()
    }
    private fun onUserLogin() {
        AuthManager.hideLogin()
        fetchContent()
    }
    private fun onUserLogout() {
        AuthManager.showLogin(supportFragmentManager, R.id.container)
    }

    override fun onResume() {
        super.onResume()

        // connect to some signals
        val loginCnx = AuthManager.UserLoggedIn.connect(object : CommandWithArgs1<Unit, Unit> {
            override fun invoke(unit : Unit) { onUserLogin() }
        });
        val logoutCnx = AuthManager.UserLoggedOut.connect(object : CommandWithArgs1<Unit, Unit> {
            override fun invoke(unit : Unit) { onUserLogout() }
        });
        connections.add(loginCnx);
        connections.add(logoutCnx);

        //
        if (!AuthManager.isAuthenticated())
        {
            AuthManager.showLogin(supportFragmentManager, R.id.container);
        }
        else
        {
            fetchContent()
        }
    }

    override fun onPause() {
        super.onPause()

        // disconnect all the connections
        for (cnx in connections)
        {
            cnx.invoke()
        }
    }
}
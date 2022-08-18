package com.itreallyiskyler.furblr.ui.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.databinding.ActivityMainBinding
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.ui.auth.LoginActivity
import com.itreallyiskyler.furblr.managers.AuthManager
import com.itreallyiskyler.furblr.managers.ContentManager
import com.itreallyiskyler.furblr.managers.SingletonManager

// TODO : Create infinite scrolling view of paged results


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    private var connections : ArrayList<()->Unit> = arrayListOf();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        // TODO : move this to Settings / profile page at some point
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        delegate.applyDayNight()

        // connect to some signals
        val logoutCnx = SingletonManager.get().AuthManager.UserLoggedOut.connect { gotoLogin() };
        connections.add(logoutCnx);

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
            R.id.navigation_profile
        )
        val appBarConfiguration = AppBarConfiguration(tabNavigationIds)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()

        // subscribe to any relevant signals
        SingletonManager.get().ContentManager.notesVM.UnreadCountChanged.connect {
            val navView : BottomNavigationView = binding.navView
            if (it > 0) {
                navView.getOrCreateBadge(R.id.navigation_notifications).number = it
            }
            else {
                navView.removeBadge(R.id.navigation_notifications)
            }
        }

        if (!SingletonManager.get().AuthManager.isAuthenticated())
        {
            gotoLogin()
        }
        else
        {
            SingletonManager.get().ContentManager.fetchStartupData()
        }
    }

    private fun gotoLogin() {
        val loginIntent : Intent = Intent(this, LoginActivity::class.java).apply {
            putExtra("test", "test")
        }
        startActivity(loginIntent)
    }

    override fun onResume() {
        super.onResume()

        // double check that we're still logged in
        if (!SingletonManager.get().AuthManager.isAuthenticated())
        {
            gotoLogin()
        }
    }

    override fun onDestroy() {
        // disconnect all the connections
        for (cnx in connections)
        {
            cnx()
        }

        super.onDestroy()
    }
}
package com.synaone.testwithings.presentation.main

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.synaone.testwithings.presentation.R
import com.synaone.testwithings.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Application main activity. Hosts most of the interesting fragments
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavigationHostFragment) as NavHostFragment
            val navigationGraph = navHostFragment.navController.navInflater.inflate(R.navigation.navigation_graph)

            navHostFragment.navController.graph = navigationGraph

            NavigationUI.setupWithNavController(
                mainNavigationView,
                navHostFragment.navController
            )

            setupActionBarWithNavController(navHostFragment.navController)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavigationHostFragment) as NavHostFragment
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }

}


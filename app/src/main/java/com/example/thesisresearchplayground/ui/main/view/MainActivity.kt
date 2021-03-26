package com.example.thesisresearchplayground.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thesisresearchplayground.R
import com.example.thesisresearchplayground.data.model.User
import com.example.thesisresearchplayground.ui.main.adapter.MainAdapter
import com.example.thesisresearchplayground.ui.main.viewmodel.MainActivityViewModel
import com.example.thesisresearchplayground.utils.Status
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.internal.immutableListOf
import org.koin.android.viewmodel.ext.android.viewModel
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(
                this, R.id.nav_host_fragment
        )

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout= findViewById(R.id.drawer_layout)
        val actionBarDrawerToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.setHomeAsUpIndicator(0)
        actionBarDrawerToggle.syncState()

        val host = Navigation.findNavController(this, R.id.nav_host_fragment)
        val appBarConfig = AppBarConfiguration(setOf(R.id.nav_landingFragment))
        toolbar.setupWithNavController(host, appBarConfig)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val acceptedIds = immutableListOf(
            R.id.nav_landingFragment
        )

        val id = item.itemId
        if (acceptedIds.contains(id)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.nav_landingFragment) {
            navController.navigate(R.id.action_nav_settingsFragment_to_nav_landingFragment)
        } else if (id == R.id.nav_settingsFragment) {
            navController.navigate(R.id.action_nav_landingFragment_to_nav_settingsFragment)
        } else {
            navController.navigate(R.id.action_nav_landingFragment_to_aboutFragment)
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
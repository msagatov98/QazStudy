package com.qazstudy

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.qazstudy.ui.TaskAdapter
import com.qazstudy.ui.LessonAdapter
import androidx.navigation.ui.navigateUp
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.fragment_lesson.*
import kotlinx.android.synthetic.main.view_holder_lesson.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.activity_navigation.*
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.nav_header_navigation.*
import kotlinx.android.synthetic.main.view_holder_lesson.view.*

class Navigation : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    var isDark = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_profile, R.id.nav_lesson, R.id.nav_task, R.id.nav_bookmark, R.id.nav_setting), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //ic_theme_switcher.setOnClickListener { themeSwitch() }
    }

    fun openProfile(v: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("isDark", isDark)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun themeSwitch(v: View) {
        isDark = !isDark
        if (isDark) {
            //container.background = resources.getDrawable(R.color.card_bg_dark)
            toolbar.background = resources.getDrawable(R.color.color)
            toolbar.setTitleTextColor(resources.getColor(R.color.dark))
            nav_view.setBackgroundColor(resources.getColor(R.color.dark))
            ic_theme_switcher.setImageDrawable(resources.getDrawable(R.drawable.ic_brightness_dark))
            ic_theme_switcher.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.color))
        } else {
            toolbar.background = resources.getDrawable(R.color.colorPrimary)
            nav_view.setBackgroundColor(resources.getColor(R.color.white))
            //container.background = resources.getDrawable(R.color.card_bg_light)
            ic_theme_switcher.setImageDrawable(resources.getDrawable(R.drawable.ic_brightness_light))
            ic_theme_switcher.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
        }

        if (fragment_lesson__recycler_view != null) fragment_lesson__recycler_view.adapter = LessonAdapter(resources.getStringArray(R.array.lessons_header), resources.getStringArray(R.array.lessons_description), isDark)
        if (fragment_task__recycler_view != null) fragment_task__recycler_view.adapter = TaskAdapter(resources.getStringArray(R.array.tasks_header), resources.getStringArray(R.array.lessons_description), isDark)

    }
}
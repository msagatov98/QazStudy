package com.qazstudy.ui.activity

import com.qazstudy.R
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.content.Intent
import com.qazstudy.util.Lesson
import androidx.navigation.ui.navigateUp
import androidx.appcompat.widget.Toolbar
import android.content.res.ColorStateList
import com.qazstudy.ui.adapter.AdapterTask
import com.qazstudy.ui.adapter.AdapterBook
import com.qazstudy.ui.adapter.AdapterLesson
import androidx.navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import kotlinx.android.synthetic.main.fragment_book.*
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.fragment_lesson.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.android.synthetic.main.navigation_header.*
import kotlinx.android.synthetic.main.navigation_app_bar.*
import kotlinx.android.synthetic.main.activity_navigation.*
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.setupActionBarWithNavController

class ActivityNavigation : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var ar : Array<Int> = arrayOf(
        R.drawable.book, R.drawable.book1, R.drawable.book2, R.drawable.book3,
        R.drawable.book, R.drawable.book1, R.drawable.book2, R.drawable.book3)

    companion object {
        var isDark = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_lesson,
                R.id.nav_task,
                R.id.nav_bookmark,
                R.id.nav_book,
                R.id.nav_dictionary,
                R.id.nav_setting
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun openProfile(v: View) {
        val intent = Intent(this, ActivityProfile::class.java)
        intent.putExtra("isDark", isDark)
        startActivity(intent)
    }

    fun themeSwitch(v: View) {

        isDark = !isDark
        if (isDark) {
            this.window.statusBarColor = getColor(R.color.light_blue)
            toolbar.setTitleTextColor(getColor(R.color.dark))
            nav_view.setBackgroundColor(getColor(R.color.dark))
            toolbar.background = getDrawable(R.color.light_blue)
            nav_header.background = getDrawable(R.color.light_blue)
            nav_header_txt_name.setTextColor(getColor(R.color.dark))
            nav_header_txt_surname.setTextColor(getColor(R.color.dark))
            ic_theme_switcher.setImageDrawable(getDrawable(R.drawable.ic_brightness_dark))
            ic_theme_switcher.backgroundTintList = ColorStateList.valueOf(getColor(R.color.light_blue))

            if (fragment_setting__constraint_layout != null) {
                fragment_setting__constraint_layout.background = getDrawable(R.color.dark)
            }
            if (fragment_bookmark__constraint_layout != null) {
                fragment_bookmark_tab_layout.background = getDrawable(R.color.light_blue)
                fragment_bookmark__constraint_layout.background = getDrawable(R.color.dark)
            }
            if (fragment_book__constraint_layout != null ) {
                fragment_book__constraint_layout.background = getDrawable(R.color.dark)
            }
        } else {
            this.window.statusBarColor = getColor(R.color.colorPrimary)
            toolbar.setTitleTextColor(getColor(R.color.white))
            nav_view.setBackgroundColor(getColor(R.color.white))
            toolbar.background = getDrawable(R.color.colorPrimary)
            nav_header.background = getDrawable(R.color.colorPrimary)
            nav_header_txt_name.setTextColor(getColor(R.color.white))
            nav_header_txt_surname.setTextColor(getColor(R.color.white))
            ic_theme_switcher.setImageDrawable(getDrawable(R.drawable.ic_brightness_light))
            ic_theme_switcher.backgroundTintList = ColorStateList.valueOf(getColor(R.color.colorPrimary))

            if (fragment_setting__constraint_layout != null) {
                fragment_setting__constraint_layout.background = getDrawable(R.color.white)
            }
            if (fragment_bookmark__constraint_layout != null) {
                fragment_bookmark_tab_layout.background = getDrawable(R.color.colorPrimary)
                fragment_bookmark__constraint_layout.background = getDrawable(R.color.white)
            }

            if (fragment_book__constraint_layout != null) {
                fragment_book__constraint_layout.background = getDrawable(R.color.white)
            }
        }

        if (fragment_lesson__recycler_view != null) {
            fragment_lesson__recycler_view.adapter =
                AdapterLesson(this, Lesson(resources.getStringArray(R.array.lessons_header), resources.getStringArray(R.array.lessons_description)), isDark)
        }

        if (fragment_task__recycler_view != null) {
            fragment_task__recycler_view.adapter =
                AdapterTask(resources.getStringArray(R.array.tasks_header), resources.getStringArray(R.array.lessons_description), isDark)
        }

        if (fragment_book__recycler_view != null) {
            fragment_book__recycler_view.adapter =
                AdapterBook(ar)
        }
    }
}
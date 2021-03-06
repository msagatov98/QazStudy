package com.qazstudy.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.qazstudy.R
import com.qazstudy.databinding.ActivityNavigationBinding
import com.qazstudy.model.Firebase
import com.qazstudy.model.Lesson
import com.qazstudy.model.Task
import com.qazstudy.model.User
import com.qazstudy.presentation.presenter.NavigationPresenter
import com.qazstudy.presentation.view.NavigationView
import com.qazstudy.ui.activity.LoginActivity.Companion.mUser
import com.qazstudy.ui.adapter.AdapterBook
import com.qazstudy.ui.adapter.AdapterLesson
import com.qazstudy.ui.adapter.AdapterTask
import com.qazstudy.util.*
import kotlinx.android.synthetic.main.fragment_book.*
import kotlinx.android.synthetic.main.fragment_lesson.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.navigation_app_bar.*
import kotlinx.android.synthetic.main.navigation_header.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class ActivityNavigation : MvpAppCompatActivity(), NavigationView {

    companion object {
        var isDark = false
        lateinit var mImageURI: Uri
    }

    private lateinit var firebase: Firebase

    @InjectPresenter
    lateinit var presenter: NavigationPresenter

    private val binding by viewBinding(ActivityNavigationBinding::inflate)

    private val TAG = javaClass.simpleName
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var ar = arrayOf(
        R.drawable.book0, R.drawable.book1, R.drawable.book2, R.drawable.book3,
        R.drawable.book, R.drawable.book1, R.drawable.book2, R.drawable.book3
    )

    private var lessonImage = arrayOf(
        R.drawable.intro, R.drawable.abc, R.drawable.reading,
        R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android,
        R.drawable.ic_android
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebase = Firebase()

        initNavigation()
    }

    override fun onStart() {
        super.onStart()

        initAuth()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_navigation_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun onIconClick(v: View) {
        when (v) {
            nav_profile -> openProfile()
            ic_theme_switcher -> themeSwitch()
            nav_header_txt_name -> openProfile()
        }
    }

    private fun initNavigation() {

        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_lesson, R.id.nav_task, R.id.nav_book,
                R.id.nav_dictionary, R.id.nav_setting
            ),
            binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    private fun initAuth() {

        firebase.mDatabase.child(NODE_USER).child(firebase.mAuth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(data: DataSnapshot) {
                    mUser = data.getValue(User::class.java)!!

                    nav_header_txt_name.text = mUser.name

                    Log.i(TAG, "onCreate: get from database $mUser")

                    if (mUser.photo.isNotEmpty() || mUser.photo != "null") {
                        firebase.mStorage.child(NODE_USER)
                            .child(firebase.mAuth.currentUser!!.uid)
                            .child(NODE_PHOTO).downloadUrl.addOnSuccessListener { imageUri ->
                                Glide.with(this@ActivityNavigation).load(imageUri.toString())
                                    .into(nav_profile)
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

    }

    private fun openProfile() {
        startActivity(Intent(this, ActivityProfile::class.java))
    }

    private fun themeSwitch() {

        isDark = !isDark

        if (isDark) {
            setDark()
        } else {
            setLight()
        }
    }

    private fun setDark() {
        binding.navView.setBackgroundColor(ContextCompat.getColor(this, R.color.dark))
        container.setBackgroundColor(ContextCompat.getColor(this, R.color.dark))
        this.window.statusBarColor = ContextCompat.getColor(this, R.color.light_blue)
        toolbar.background = ContextCompat.getDrawable(this, R.color.light_blue)
        nav_header.background = ContextCompat.getDrawable(this, R.color.light_blue)
        ic_theme_switcher.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_blue))
        ic_theme_switcher.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_brightness_light
            )
        )

        if (fragment_setting__constraint_layout != null) {
            fragment_setting__constraint_layout.background =
                ContextCompat.getDrawable(this, R.color.dark)
        }
        if (fragment_book__recycler_view != null) {
            fragment_book__recycler_view.background =
                ContextCompat.getDrawable(this, R.color.dark)
        }

        if (fragment_lesson__recycler_view != null) {
            fragment_lesson__recycler_view.adapter =
                AdapterLesson(
                    this,
                    Lesson(lessonImage, resources.getStringArray(R.array.lessons_header))
                )
        }
        if (fragment_task__recycler_view != null) {
            fragment_task__recycler_view.adapter =
                AdapterTask(this, Task(resources.getStringArray(R.array.tasks_header)))
        }
        if (fragment_book__recycler_view != null) {
            fragment_book__recycler_view.adapter =
                AdapterBook(this, ar)
        }
    }

    private fun setLight() {
        binding.navView.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        container.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        this.window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        toolbar.background = ContextCompat.getDrawable(this, R.color.colorPrimary)
        nav_header.background = ContextCompat.getDrawable(this, R.color.colorPrimary)
        ic_theme_switcher.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimary))
        ic_theme_switcher.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_brightness_dark
            )
        )

        if (fragment_setting__constraint_layout != null) {
            fragment_setting__constraint_layout.background =
                ContextCompat.getDrawable(this, R.color.white)
        }
        if (fragment_book__recycler_view != null) {
            fragment_book__recycler_view.background =
                ContextCompat.getDrawable(this, R.color.white)
        }


        if (fragment_lesson__recycler_view != null) {
            fragment_lesson__recycler_view.adapter =
                AdapterLesson(
                    this,
                    Lesson(lessonImage, resources.getStringArray(R.array.lessons_header))
                )
        }
        if (fragment_task__recycler_view != null) {
            fragment_task__recycler_view.adapter =
                AdapterTask(this, Task(resources.getStringArray(R.array.tasks_header)))
        }
        if (fragment_book__recycler_view != null) {
            fragment_book__recycler_view.adapter =
                AdapterBook(this, ar)
        }
    }
}
package com.qazstudy.ui.activity

import com.qazstudy.R
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.content.Intent
import com.qazstudy.model.Task
import com.qazstudy.model.User
import com.bumptech.glide.Glide
import com.qazstudy.model.Lesson
import androidx.navigation.ui.navigateUp
import androidx.appcompat.widget.Toolbar
import android.content.res.ColorStateList
import android.util.Log
import androidx.core.content.ContextCompat
import com.qazstudy.ui.adapter.AdapterTask
import com.qazstudy.ui.adapter.AdapterBook
import com.qazstudy.ui.adapter.AdapterLesson
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_book.*
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.fragment_lesson.*
import com.qazstudy.ui.adapter.ValueEventListenerAdapter
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.navigation_header.*
import kotlinx.android.synthetic.main.navigation_app_bar.*
import kotlinx.android.synthetic.main.activity_navigation.*
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.setupActionBarWithNavController
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.qazstudy.util.*

class ActivityNavigation : AppCompatActivity() {


    private val AUTH = FirebaseAuth.getInstance()
    private var STORAGE = FirebaseStorage.getInstance().reference
    private var DATABASE = FirebaseDatabase.getInstance().reference

    private val TAG = javaClass.simpleName
    private val AUTH_REQUEST_CODE = 101
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var authListener: FirebaseAuth.AuthStateListener? = null

    private var ar = arrayOf(
        R.drawable.book0, R.drawable.book1, R.drawable.book2, R.drawable.book3,
        R.drawable.book, R.drawable.book1, R.drawable.book2, R.drawable.book3
    )

    private var lessonImage = arrayOf(
        R.drawable.intro, R.drawable.abc, R.drawable.reading,
        R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android,
        R.drawable.ic_android
    )

    private var providers: List<AuthUI.IdpConfig> = listOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build(),
        //AuthUI.IdpConfig.TwitterBuilder().build(), waiting for twitter gives access to their server
        AuthUI.IdpConfig.FacebookBuilder().build()
    )

    companion object {
        lateinit var mUser: User
        lateinit var mImageURI: Uri
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        authListener = FirebaseAuth.AuthStateListener {
            if (AUTH.currentUser == null) {
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .build(),
                    AUTH_REQUEST_CODE
                )
            } else {
                DATABASE.child(NODE_USER).child(AUTH.currentUser!!.uid)
                    .addListenerForSingleValueEvent(ValueEventListenerAdapter {

                        mUser = it.getValue(User::class.java)!!

                        if (mUser.isDark) {
                            setDark()
                        } else {
                            setLight()
                        }

                        nav_header_txt_name.text = mUser.name

                        Log.i(TAG, "onCreate: get from database $mUser")

                        if (mUser.photo.isNotEmpty() || mUser.photo != "null") {
                            STORAGE.child(NODE_USER).child(AUTH.currentUser!!.uid)
                                .child(NODE_PHOTO).downloadUrl.addOnSuccessListener { imageUri ->
                                    Glide.with(this@ActivityNavigation).load(imageUri.toString())
                                        .into(nav_profile)
                                }
                        }
                    })
            }
        }

        AUTH.addAuthStateListener(authListener!!)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_lesson, R.id.nav_task, R.id.nav_book,
                R.id.nav_dictionary, R.id.nav_setting
            ),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }


    override fun onStart() {
        super.onStart()

        AUTH.addAuthStateListener(authListener!!)

        if (AUTH.currentUser != null) {
            DATABASE.child(NODE_USER).child(AUTH.currentUser!!.uid)
                .addListenerForSingleValueEvent(ValueEventListenerAdapter {

                    mUser = it.getValue(User::class.java)!!

                    if (mUser.isDark) {
                        setDark()
                    } else {
                        setLight()
                    }

                    nav_header_txt_name.text = mUser.name

                    Log.i(TAG, "onCreate: get from database $mUser")

                    if (mUser.photo.isNotEmpty() || mUser.photo != "null") {
                        STORAGE.child(NODE_USER).child(AUTH.currentUser!!.uid)
                            .child(NODE_PHOTO).downloadUrl.addOnSuccessListener { imageUri ->
                                Glide.with(this@ActivityNavigation).load(imageUri.toString())
                                    .into(nav_profile)
                            }
                    }
                })
        }

    }

    override fun onStop() {
        if (authListener != null)
            AUTH.removeAuthStateListener(authListener!!)
        super.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == RESULT_OK && requestCode == AUTH_REQUEST_CODE) {

            mUser = User(
                name = AUTH.currentUser!!.displayName.toString(),
                email = AUTH.currentUser!!.email.toString(),
                photo = AUTH.currentUser!!.photoUrl.toString(),
            )

            DATABASE.child(NODE_USER).child(AUTH.currentUser!!.uid).setValue(mUser)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        nav_header_txt_name.text = mUser.name

                        if (mUser.photo.isNotEmpty() || mUser.photo != "null") {

                            mImageURI = mUser.photo.toUri()

                            STORAGE.child(NODE_USER).child(AUTH.currentUser!!.uid).child(NODE_PHOTO)
                                .putFile(mImageURI)
                                .addOnCompleteListener { uploadTask ->
                                    if (uploadTask.isSuccessful) {
                                        Glide.with(this).load(mImageURI).into(nav_profile)
                                    } else {
                                        showToast(uploadTask.exception!!.message.toString())
                                    }
                                }
                        }
                    } else {
                        showToast("Error creating user")
                    }
                }

            startActivity(Intent(this, ActivityNavigation::class.java))
            finish()
        }

        super.onActivityResult(requestCode, resultCode, data)
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

    private fun openProfile() {
        startActivity(Intent(this, ActivityProfile::class.java))
    }

    private fun themeSwitch() {


        val updatesMap = mutableMapOf<String, Any>()

        updatesMap["isDark"] = !mUser.isDark

        DATABASE.child(NODE_USER).child(AUTH.currentUser!!.uid).updateChildren(updatesMap)

        mUser.isDark = !mUser.isDark

        if (mUser.isDark) {
            setDark()
        } else {
            setLight()
        }
    }

    private fun setDark() {
        nav_view.setBackgroundColor(getColor(R.color.dark))
        container.setBackgroundColor(getColor(R.color.dark))
        this.window.statusBarColor = getColor(R.color.light_blue)
        toolbar.background = ContextCompat.getDrawable(this, R.color.light_blue)
        nav_header.background = ContextCompat.getDrawable(this, R.color.light_blue)
        ic_theme_switcher.backgroundTintList =
            ColorStateList.valueOf(getColor(R.color.light_blue))
        ic_theme_switcher.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_brightness_dark
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
        nav_view.setBackgroundColor(getColor(R.color.white))
        container.setBackgroundColor(getColor(R.color.white))
        this.window.statusBarColor = getColor(R.color.colorPrimary)
        toolbar.background = ContextCompat.getDrawable(this, R.color.colorPrimary)
        nav_header.background = ContextCompat.getDrawable(this, R.color.colorPrimary)
        ic_theme_switcher.backgroundTintList =
            ColorStateList.valueOf(getColor(R.color.colorPrimary))
        ic_theme_switcher.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_brightness_light
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
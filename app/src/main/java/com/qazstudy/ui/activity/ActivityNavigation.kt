package com.qazstudy.ui.activity

import com.qazstudy.R
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.content.Intent
import com.qazstudy.model.Task
import com.qazstudy.model.Lesson
import androidx.navigation.ui.navigateUp
import androidx.appcompat.widget.Toolbar
import android.content.res.ColorStateList
import com.qazstudy.ui.adapter.AdapterTask
import com.qazstudy.ui.adapter.AdapterBook
import com.qazstudy.ui.adapter.AdapterLesson
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.database.FirebaseDatabase
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_book.*
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.fragment_lesson.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.android.synthetic.main.navigation_header.*
import kotlinx.android.synthetic.main.navigation_app_bar.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.android.synthetic.main.activity_navigation.*
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.qazstudy.model.User
import com.qazstudy.ui.adapter.ValueEventListenerAdapter

class ActivityNavigation : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var ar : Array<Int> = arrayOf(
        R.drawable.book, R.drawable.book1, R.drawable.book2, R.drawable.book3,
        R.drawable.book, R.drawable.book1, R.drawable.book2, R.drawable.book3)

    private var lessonImage = arrayOf(R.drawable.intro, R.drawable.abc, R.drawable.reading,
        R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android,
        R.drawable.ic_android)

    companion object {
        var isDark = false
        lateinit var mUser: User
        lateinit var mImageURI: Uri
        lateinit var mGoogleSignInClient: GoogleSignInClient
        var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        var mStorage: StorageReference = FirebaseStorage.getInstance().reference
        var mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (mAuth.currentUser == null) {
            startActivity(Intent(this, ActivityLogin::class.java))
            finish()
        } else  {
            mDatabase.child("users/${mAuth.currentUser!!.uid}")
                .addListenerForSingleValueEvent(ValueEventListenerAdapter {
                    mUser = it.getValue(User::class.java)!!
                    nav_header_txt_name.text = mUser.name
                    if (mUser.photo.isNotEmpty()) {
                        mStorage.child("users/${mAuth.currentUser!!.uid}/photo").downloadUrl.addOnSuccessListener {
                            Glide.with(this).load(it.toString()).into(nav_profile)
                        }
                    }
                })
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_lesson, R.id.nav_task, R.id.nav_book,
                R.id.nav_dictionary, R.id.nav_setting), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (isDark) ic_theme_switcher.setImageDrawable(getDrawable(R.drawable.ic_brightness_dark))
        else ic_theme_switcher.setImageDrawable(getDrawable(R.drawable.ic_brightness_light))
    }

    override fun onStart() {
        super.onStart()

        if (mAuth.currentUser == null) {
            startActivity(Intent(this, ActivityLogin::class.java))
            finish()
        }

        mDatabase.child("users/${mAuth.currentUser!!.uid}").addListenerForSingleValueEvent( ValueEventListenerAdapter{
            nav_header_txt_name.text = mUser.name
            if (mUser.photo.isNotEmpty()) {
                mStorage.child("users/${mAuth.currentUser!!.uid}/photo").downloadUrl.addOnSuccessListener {
                    Glide.with(this).load(it.toString()).into(nav_profile)
                }
            }
        })
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
        startActivity(Intent(this, ActivityProfile::class.java))
    }

    fun themeSwitch(v: View) {

        isDark = !isDark

        if (isDark) {
            nav_view.setBackgroundColor(getColor(R.color.dark))
            toolbar.background = getDrawable(R.color.light_blue)
            nav_header.background = getDrawable(R.color.light_blue)
            nav_header_txt_name.setTextColor(getColor(R.color.dark))
            this.window.statusBarColor = getColor(R.color.light_blue)
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
            toolbar.setTitleTextColor(getColor(R.color.white))
            nav_view.setBackgroundColor(getColor(R.color.white))
            toolbar.background = getDrawable(R.color.colorPrimary)
            nav_header.background = getDrawable(R.color.colorPrimary)
            nav_header_txt_name.setTextColor(getColor(R.color.white))
            this.window.statusBarColor = getColor(R.color.colorPrimary)
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
                AdapterLesson(
                    this,
                    Lesson(lessonImage, resources.getStringArray(R.array.lessons_header), resources.getStringArray(R.array.lessons_description)))
        }
        if (fragment_task__recycler_view != null) {
            fragment_task__recycler_view.adapter =
                AdapterTask(
                    this,
                    Task(resources.getStringArray(R.array.tasks_header), resources.getStringArray(R.array.lessons_description)))
        }
        if (fragment_book__recycler_view != null) {
            fragment_book__recycler_view.adapter =
                AdapterBook(ar)
        }
    }
}
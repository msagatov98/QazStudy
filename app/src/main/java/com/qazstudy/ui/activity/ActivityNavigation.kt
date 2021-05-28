package com.qazstudy.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
import com.qazstudy.model.User
import com.qazstudy.ui.activity.LoginActivity.Companion.mUser
import com.qazstudy.util.*
import kotlinx.android.synthetic.main.fragment_book.*
import kotlinx.android.synthetic.main.fragment_lesson.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.navigation_app_bar.*
import kotlinx.android.synthetic.main.navigation_header.ic_theme_switcher
import kotlinx.android.synthetic.main.navigation_header.nav_header_txt_name
import kotlinx.android.synthetic.main.navigation_header.nav_profile

class ActivityNavigation : AppCompatActivity() {

    companion object {
        lateinit var mImageURI: Uri
    }

    private lateinit var firebase: Firebase


    private val binding by viewBinding(ActivityNavigationBinding::inflate)

    private val TAG = javaClass.simpleName
    private lateinit var appBarConfiguration: AppBarConfiguration

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
                                Glide.with(this@ActivityNavigation)
                                    .load(imageUri.toString())
                                    .circleCrop()
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

    }
}
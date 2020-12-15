package com.qazstudy.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.qazstudy.R
import com.qazstudy.databinding.ActivityLectureBinding
import com.qazstudy.ui.activity.LoginActivity.Companion.mUser
import com.qazstudy.ui.fragment.lecture.*
import com.qazstudy.util.viewBinding

class ActivityLecture : AppCompatActivity() {

    private var position = -1

    private lateinit var openedFragment: Fragment
    private var fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

    private val binding by viewBinding(ActivityLectureBinding::inflate)

    private val arFragmentLecture = arrayListOf(
        FragmentLectureIntro(),
        FragmentLecture1(),
        FragmentLecture2(),
        FragmentLecture3(),
        FragmentLecture4(),
        FragmentLecture5(),
        FragmentLecture6(),
        FragmentLecture7(),
        FragmentLecture8(),
        FragmentLecture9()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setTheme()
        initFragments()
    }

    fun onIconClick(v: View) {
        when (v) {
            binding.activityLessonIcBack -> finish()
            binding.activityLessonIcChat -> onIconChatClick()
            binding.activityLessonIcNext -> onIconNextClick()
            binding.activityLessonIcPrevious -> onIconPreviousClick()
        }
    }

    private fun initFragments() {

        position = intent.getIntExtra("numLesson", -1)

        for (i in arFragmentLecture.indices) {
            if (intent.getIntExtra("numLesson", -1) == i) {
                openedFragment = arFragmentLecture[i]
                fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container_lecture, arFragmentLecture[i])
                fragmentTransaction.commit()
            }
        }
    }

    private fun setTheme() {
        if (mUser.isDark) {
            this.window.statusBarColor = getColor(R.color.light_blue)
            binding.activityLessonTxtLectureHeader.setTextColor(getColor(R.color.dark))
            binding.activityLessonToolbar.setBackgroundColor(getColor(R.color.light_blue))
            binding.activityLessonConstraintLayout.setBackgroundColor(getColor(R.color.dark))
            binding.activityLessonToolbarBottom.setBackgroundColor(getColor(R.color.light_blue))
            binding.activityLessonIcChat.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_chat_dark
                )
            )
            binding.activityLessonIcBack.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_back_dark
                )
            )
            binding.activityLessonIcNext.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_next_dark
                )
            )
            binding.activityLessonIcPrevious.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_previous_dark
                )
            )
        }
    }

    private fun onIconChatClick() {
        val intentChat = Intent(this, ActivityChat::class.java)
        intentChat.putExtra("numChat", position)
        startActivity(intentChat)
    }

    private fun onIconNextClick() {

        fragmentTransaction = supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left)

        if (position < arFragmentLecture.size - 1)
            position++

        when (openedFragment) {
            is FragmentLectureIntro -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture1()
                )
                openedFragment = FragmentLecture1()
            }
            is FragmentLecture1 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture2()
                )
                openedFragment = FragmentLecture2()
            }
            is FragmentLecture2 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture3()
                )
                openedFragment = FragmentLecture3()
            }
            is FragmentLecture3 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture4()
                )
                openedFragment = FragmentLecture4()
            }
            is FragmentLecture4 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture5()
                )
                openedFragment = FragmentLecture5()
            }
            is FragmentLecture5 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture6()
                )
                openedFragment = FragmentLecture6()
            }
            is FragmentLecture6 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture7()
                )
                openedFragment = FragmentLecture7()
            }
            is FragmentLecture7 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture8()
                )
                openedFragment = FragmentLecture8()
            }
            is FragmentLecture8 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture9()
                )
                openedFragment = FragmentLecture9()
            }
        }

        fragmentTransaction.commit()
    }

    private fun onIconPreviousClick() {

        fragmentTransaction = supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right)

        if (position > 0)
            position--

        when (openedFragment) {
            is FragmentLecture1 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLectureIntro()
                )
                openedFragment =
                    FragmentLectureIntro()
            }
            is FragmentLecture2 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture1()
                )
                openedFragment =
                    FragmentLecture1()
            }
            is FragmentLecture3 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture2()
                )
                openedFragment =
                    FragmentLecture2()
            }
            is FragmentLecture4 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture3()
                )
                openedFragment =
                    FragmentLecture3()
            }
            is FragmentLecture5 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture4()
                )
                openedFragment =
                    FragmentLecture4()
            }
            is FragmentLecture6 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture5()
                )
                openedFragment =
                    FragmentLecture5()
            }
            is FragmentLecture7 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture6()
                )
                openedFragment =
                    FragmentLecture6()
            }
            is FragmentLecture8 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture7()
                )
                openedFragment =
                    FragmentLecture7()
            }
            is FragmentLecture9 -> {
                fragmentTransaction.replace(
                    R.id.fragment_container_lecture,
                    FragmentLecture8()
                )
                openedFragment =
                    FragmentLecture8()
            }
        }

        fragmentTransaction.commit()
    }
}
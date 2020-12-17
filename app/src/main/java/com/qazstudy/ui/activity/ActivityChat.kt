package com.qazstudy.ui.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.qazstudy.R
import com.qazstudy.databinding.ActivityChatBinding
import com.qazstudy.presentation.presenter.ChatPresenter
import com.qazstudy.presentation.view.ChatView
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.util.showToast
import com.qazstudy.util.viewBinding
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ActivityChat : MvpAppCompatActivity(), ChatView {

    @InjectPresenter
    lateinit var mChatPresenter: ChatPresenter

    @ProvidePresenter
    fun provideChatPresenter(): ChatPresenter {
        return ChatPresenter(this)
    }

    private val binding by viewBinding(ActivityChatBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setMode()
        initListeners()

        val position = intent.getIntExtra("numChat", -1)

        displayChat(position)

        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        manager.stackFromEnd = true

        binding.rvChat.layoutManager = manager
        binding.rvChat.adapter = mChatPresenter.getAdapter()

    }

    override fun onStart() {
        super.onStart()
        mChatPresenter.getAdapter().startListening()
    }

    override fun onPause() {
        super.onPause()
        mChatPresenter.getAdapter().stopListening()
    }

    override fun setMode() {
        if (isDark) {
            binding.bgChat.setBackgroundColor(getColor(R.color.dark))
            this.window.statusBarColor = getColor(R.color.black)
            binding.activityChatToolbar.setBackgroundColor(getColor(R.color.light_blue))

            binding.inputChatMessage.setTextColor(getColor(R.color.white))
            binding.inputChatMessage.background = ContextCompat.getDrawable(
                this,
                R.drawable.bg_input_chat_message_dark
            )
        }
    }

    override fun displayChat(i: Int) {
        mChatPresenter.displayChat(i)
    }

    private fun initListeners() {
        binding.activityChatIcBack.setOnClickListener { finish() }

        binding.icSend.setOnClickListener {
            if (binding.inputChatMessage.text.isNotEmpty()) {
                mChatPresenter.sendMessage(binding.inputChatMessage.text.toString())
                mChatPresenter.getAdapter().notifyDataSetChanged()
                binding.inputChatMessage.setText("")
            } else {
                showToast(R.string.empty_message_send)
            }
        }
    }
}
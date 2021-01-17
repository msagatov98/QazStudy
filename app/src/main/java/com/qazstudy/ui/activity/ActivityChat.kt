package com.qazstudy.ui.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.qazstudy.R
import com.qazstudy.databinding.ActivityChatBinding
import com.qazstudy.model.Message
import com.qazstudy.presentation.presenter.ChatPresenter
import com.qazstudy.presentation.view.ChatView
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import com.qazstudy.ui.adapter.AdapterChat
import com.qazstudy.util.NODE_MESSAGE
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

    private lateinit var adapter: FirebaseRecyclerAdapter<Message, AdapterChat.MessageViewHolder>
    private lateinit var options: FirebaseRecyclerOptions<Message>
    private lateinit var mChatPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMode()
        initListeners()

        val position = intent.getIntExtra("numChat", -1)

        displayChat(position)

        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        manager.stackFromEnd = true

        binding.rvChat.layoutManager = manager
        binding.rvChat.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onPause() {
        super.onPause()
        adapter.stopListening()
    }

    override fun setMode() {
        if (isDark) {
            this.window.statusBarColor = ContextCompat.getColor(this, R.color.black)
            binding.run {
                bgChat.setBackgroundColor(ContextCompat.getColor(this@ActivityChat, R.color.dark))
                activityChatToolbar.setBackgroundColor(ContextCompat.getColor(this@ActivityChat, R.color.light_blue))
                inputChatMessage.setTextColor(ContextCompat.getColor(this@ActivityChat, R.color.white))
                inputChatMessage.background = ContextCompat.getDrawable(
                    this@ActivityChat,
                    R.drawable.bg_input_chat_message_dark
                )
            }
        }
    }

    override fun displayChat(position: Int) {

        mChatPath = when (position) {
            0 -> "$NODE_MESSAGE/lectureIntro"
            1 -> "$NODE_MESSAGE/lecture1"
            2 -> "$NODE_MESSAGE/lecture2"
            3 -> "$NODE_MESSAGE/lecture3"
            4 -> "$NODE_MESSAGE/lecture4"
            5 -> "$NODE_MESSAGE/lecture5"
            6 -> "$NODE_MESSAGE/lecture6"
            7 -> "$NODE_MESSAGE/lecture7"
            8 -> "$NODE_MESSAGE/lecture8"
            9 -> "$NODE_MESSAGE/lecture9"
            else -> ""
        }

        val query = mChatPresenter.firebase.mDatabase.child(mChatPath)

        options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(query, Message::class.java)
            .build()

        adapter = AdapterChat(options, this)
    }

    override fun initPath() {
        mChatPresenter.initPath(mChatPath)
    }

    override fun sendMessage() {
        mChatPresenter.sendMessage(binding.inputChatMessage.text.toString())
        adapter.notifyDataSetChanged()
        binding.inputChatMessage.setText("")
    }

    override fun goToBottom() {
        binding.rvChat.scrollToPosition(adapter.itemCount-1)
    }

    private fun initListeners() {
        binding.activityChatIcBack.setOnClickListener { finish() }

        binding.icSend.setOnClickListener {
            if (binding.inputChatMessage.text.isNotEmpty()) {
                sendMessage()
            } else {
                showToast(R.string.empty_message_send)
            }
        }
    }
}
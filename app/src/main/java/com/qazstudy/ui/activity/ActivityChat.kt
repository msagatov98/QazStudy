package com.qazstudy.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.qazstudy.R
import com.qazstudy.databinding.ActivityChatBinding
import com.qazstudy.model.Message
import com.qazstudy.ui.adapter.AdapterChat
import com.qazstudy.util.NODE_MESSAGE
import com.qazstudy.util.showToast
import com.qazstudy.util.viewBinding

class ActivityChat : AppCompatActivity() {

    private val binding by viewBinding(ActivityChatBinding::inflate)

    private lateinit var adapter: FirebaseRecyclerAdapter<Message, AdapterChat.MessageViewHolder>
    private lateinit var options: FirebaseRecyclerOptions<Message>
    private lateinit var mChatPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    fun displayChat(position: Int) {

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


        adapter = AdapterChat(options, this)
    }


    private fun initListeners() {
        binding.activityChatIcBack.setOnClickListener { finish() }

        binding.icSend.setOnClickListener {
            if (binding.inputChatMessage.text.isNotEmpty()) {
            } else {
                showToast(R.string.empty_message_send)
            }
        }
    }
}
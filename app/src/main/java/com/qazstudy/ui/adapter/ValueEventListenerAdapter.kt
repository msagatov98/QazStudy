package com.qazstudy.ui.adapter

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

open class ValueEventListenerAdapter(val handler: (DataSnapshot) -> Unit): ValueEventListener {
    override fun onCancelled(error: DatabaseError) {}

    override fun onDataChange(data: DataSnapshot) {
        if (data.exists())
            handler(data)
    }
}
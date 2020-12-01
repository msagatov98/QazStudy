package com.qazstudy.presentation.presenter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.qazstudy.model.FirebaseHelper
import com.qazstudy.presentation.view.NavigationView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class NavigationPresenter(val firebase: FirebaseHelper): MvpPresenter<NavigationView>() {

    init {
        firebase.mAuth = FirebaseAuth.getInstance()
        firebase.mStorage = FirebaseStorage.getInstance().reference
        firebase.mDatabase = FirebaseDatabase.getInstance().reference
    }


}
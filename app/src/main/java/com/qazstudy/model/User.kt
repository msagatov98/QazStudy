package com.qazstudy.model

data class User(val name: String = "",
                val city: String = "",
                val email: String = "",
                val photo: String = "",
                val country: String = "",
                val password: String = "********",
                var isDark: Boolean = false)
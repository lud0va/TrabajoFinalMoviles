package com.example.apolloproject.data.retrofit

import dagger.Component
import javax.inject.Inject
import javax.inject.Singleton


data class CacheAuthorization (
    var accesToken: String = "",
    var refreshToken: String = "",
    var user : String  ="",
    var pass : String = "",

    )

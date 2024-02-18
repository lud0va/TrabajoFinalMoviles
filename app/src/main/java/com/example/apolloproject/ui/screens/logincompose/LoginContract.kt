package com.example.apolloproject.ui.screens.logincompose

interface LoginContract {
    sealed class Event(){
        object login: Event()
        object register: Event()
        class CambiarLoginSuccess(val flag:Boolean):Event()
        class CambiarUserState(val username: String):Event()
        class CambiarPasswState(val passw: String):Event()



    }
    data class State(

        val username:String?="luis",
        val password:String?="luis",
        val  error:String?=null,
        val loginsucces:Boolean=false,
        val registersuccess:Boolean=false

        )


}
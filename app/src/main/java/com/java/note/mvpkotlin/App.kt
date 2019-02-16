package com.java.note.mvpkotlin

import android.app.Application
import di.DaggerUsersComponent
import di.UsersComponent
import di.UsersModule

class App: Application() {

    lateinit var component: UsersComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerUsersComponent.builder()
                .usersModule(UsersModule(this)).build()
    }

    companion object {
        lateinit var instance: App private set
    }
}
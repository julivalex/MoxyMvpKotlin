package di

import adapter.UserAdapter
import android.content.Context
import dagger.Module
import dagger.Provides
import database.DbHelper
import interactors.UsersInteractor
import interactors.UsersInteractorImpl
import model.UserData

@Module
class UsersModule(private val context: Context) {

    @Provides
    @UsersScope
    fun provideContext() = context

    @Provides
    @UsersScope
    fun provideDbHelper() = DbHelper(context)

    @Provides
    @UsersScope
    fun provideUsersInteractor(dbHelper: DbHelper): UsersInteractor
            = UsersInteractorImpl(dbHelper)

    @Provides
    @UsersScope
    fun provideUserAdapter() = UserAdapter()

    @Provides
    @UsersScope
    fun provideUserData() = UserData()
}
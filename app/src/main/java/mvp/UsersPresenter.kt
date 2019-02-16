package mvp

import android.content.ContentValues
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.java.note.mvpkotlin.R
import database.UserTable
import interactors.UsersInteractor
import model.UserData
import javax.inject.Inject

@InjectViewState
class UsersPresenter @Inject constructor(
        private val usersInteractor: UsersInteractor,
        private var userData: UserData
) : MvpPresenter<UsersView>() {

    fun loadUsers() {
        usersInteractor.loadUsers { viewState.showUsers(it) }
    }

    fun add() {
        viewState.updateUserDate()
        if (userData.name.isEmpty() || userData.email.isEmpty()) {
            viewState.showToast(R.string.empty_values)
            return
        }

        val contentValues = ContentValues(2)
        contentValues.put(UserTable.Column.NAME, userData.name)
        contentValues.put(UserTable.Column.EMAIL, userData.email)

        viewState.showProgress()
        usersInteractor.addUser(contentValues) {
            viewState.hideProgress()
            loadUsers()
        }
    }

    fun clear() {
        viewState.showProgress()
        usersInteractor.clearUsers {
            viewState.hideProgress()
            loadUsers()
        }
    }

    fun updateUserDate(name: String, email: String){
        userData.name = name
        userData.email = email
    }

}
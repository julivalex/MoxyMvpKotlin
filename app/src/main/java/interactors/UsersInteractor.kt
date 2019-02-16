package interactors

import android.content.ContentValues
import model.User

interface UsersInteractor {

    fun loadUsers(load: (List<User>) -> Unit)

    fun addUser(contentValues: ContentValues, complete: () -> Unit)

    fun clearUsers(complete: () -> Unit)
}
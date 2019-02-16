package interactors

import android.content.ContentValues
import android.database.Cursor
import database.DbHelper

import java.util.LinkedList
import database.UserTable
import model.User
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UsersInteractorImpl @Inject constructor(
        private val dbHelper: DbHelper
) : UsersInteractor {

    override fun loadUsers(load: (List<User>) -> Unit) {
        Observable.fromCallable {
            val users: LinkedList<User> = LinkedList()
            val cursor: Cursor = dbHelper.readableDatabase
                    .query(UserTable.TABLE,
                            null, null, null,
                            null, null, null)
            while (cursor.moveToNext()) {
                val id: Long = cursor.getLong(cursor.getColumnIndex(UserTable.Column.ID))
                val name: String = cursor.getString(cursor.getColumnIndex(UserTable.Column.NAME))
                val email: String = cursor.getString(cursor.getColumnIndex(UserTable.Column.EMAIL))

                val user = User(id, name, email)
                users.add(user)

            }
            cursor.close()
            return@fromCallable users
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    load(it)
                }

    }

    override fun addUser(contentValues: ContentValues, complete: () -> Unit) {
        Observable.fromCallable {
            dbHelper.writableDatabase.insert(UserTable.TABLE, null, contentValues);
            try {
                TimeUnit.SECONDS.sleep(1)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            return@fromCallable null
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    complete()
                }
    }

    override fun clearUsers(complete: () -> Unit) {
        Observable.fromCallable {
            dbHelper.writableDatabase.delete(UserTable.TABLE, null, null)
            try {
                TimeUnit.SECONDS.sleep(1)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            return@fromCallable null
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    complete()
                }
    }
}
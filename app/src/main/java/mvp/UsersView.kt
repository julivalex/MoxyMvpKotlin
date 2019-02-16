package mvp

import com.arellomobile.mvp.MvpView
import model.User

interface UsersView : MvpView {

    fun updateUserDate()

    fun showUsers(users: List<User>)

    fun showToast(resId: Int)

    fun showProgress()

    fun hideProgress()
}
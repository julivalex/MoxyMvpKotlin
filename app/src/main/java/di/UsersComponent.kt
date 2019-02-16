package di

import dagger.Component
import com.java.note.mvpkotlin.UsersActivity

@UsersScope
@Component(modules = [UsersModule::class])
interface UsersComponent {
    fun inject(activity: UsersActivity)
}
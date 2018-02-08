package org.lynxz.lifecyclerobserver.activity

import android.content.Intent
import org.lynxz.lifecyclerobserver.ActivityLifecycleManager
import org.lynxz.lifecyclerobserver.Logger
import org.lynxz.lifecyclerobserver.base.BaseLaunchModeActivity

// 带 taskAffinity 的 launchMode = "singleTask" 的 activity
// 无需添加  Intent.FLAG_ACTIVITY_NEW_TASK 也会生效, 这点跟 launchMode=standard 不同
class SingleTaskDActivity : BaseLaunchModeActivity() {

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Logger.d("${this::class.java.simpleName} onNewIntent ${this.hashCode()}", ActivityLifecycleManager.TAG_ACTIVITY)
    }
}
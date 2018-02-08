package org.lynxz.lifecyclerobserver.activity

import android.content.Intent
import org.lynxz.lifecyclerobserver.ActivityLifecycleManager
import org.lynxz.lifecyclerobserver.Logger
import org.lynxz.lifecyclerobserver.base.BaseLaunchModeActivity

class SingleInstanceAActivity : BaseLaunchModeActivity() {
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Logger.d("${this::class.java.simpleName} onNewIntent ${this.hashCode()}", ActivityLifecycleManager.TAG_ACTIVITY)
    }
}
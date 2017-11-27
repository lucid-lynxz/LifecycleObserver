package org.lynxz.lifecyclerobserver

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import org.lynxz.lifecyclerobserver.receiver.KeyEventReceiver


/**
 * Created by lynxz on 25/10/2017.
 */
class LifeApplication : Application() {
    private val TAG = "LifeApplication"
    override fun onCreate() {
        super.onCreate()
        Logger.init(Logger.debugLevel, TAG)

        registerReceiver(KeyEventReceiver(), IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))

        TaskTreeManager.instance.init(this)
        this.registerActivityLifecycleCallbacks(ActivityLifecycleManager)
    }
}
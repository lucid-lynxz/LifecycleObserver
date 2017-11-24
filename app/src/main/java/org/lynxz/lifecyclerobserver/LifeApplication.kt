package org.lynxz.lifecyclerobserver

import android.app.Application

/**
 * Created by lynxz on 25/10/2017.
 */
class LifeApplication : Application() {
    private val TAG = "LifeApplication"
    override fun onCreate() {
        super.onCreate()
        Logger.init(Logger.debugLevel, TAG)
        TaskTreeManager.instance.init(this)
        this.registerActivityLifecycleCallbacks(ActivityLifecycleManager)
    }
}
package org.lynxz.lifecyclerobserver

import android.app.Application

/**
 * Created by lynxz on 25/10/2017.
 */
class LifeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Logger.init(Logger.debugLevel, "lifeCycleLog")
        TaskTreeManager.instance.init(this)
        this.registerActivityLifecycleCallbacks(ActivityLifecycleManager)
    }
}
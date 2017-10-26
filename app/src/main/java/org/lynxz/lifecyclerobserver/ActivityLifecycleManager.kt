package org.lynxz.lifecyclerobserver

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Created by lynxz on 25/10/2017.
 * 管理所有的activity生命周期,打印日志信息,绘制task堆栈树
 */
object ActivityLifecycleManager : Application.ActivityLifecycleCallbacks {
    private val TAG_ACTIVITY = "activity_life_cycle"

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        activity?.let {
            Logger.d("${it::class.java.simpleName} onCreate ${it.hashCode()}, taskId = ${it.taskId}", TAG_ACTIVITY)
//            RxPermissions(it).request(Manifest.permission.SYSTEM_ALERT_WINDOW)
//                    .subscribe {
//                        it.no { activity.showToast("本应用需要绘制悬浮窗权限") }
//                    }
        }
    }

    override fun onActivityStarted(activity: Activity?) {
        activity?.let {
            Logger.d("${it::class.java.simpleName} onStart ${it.hashCode()}", TAG_ACTIVITY)
        }
    }

    override fun onActivityResumed(activity: Activity?) {
        activity?.let {
            Logger.d("${it::class.java.simpleName} onResume ${it.hashCode()}", TAG_ACTIVITY)
        }
    }

    override fun onActivityPaused(activity: Activity?) {
        activity?.let {
            Logger.d("${it::class.java.simpleName} onPause ${it.hashCode()}", TAG_ACTIVITY)
        }
    }

    override fun onActivityStopped(activity: Activity?) {
        activity?.let {
            Logger.d("${it::class.java.simpleName} onStop ${it.hashCode()}", TAG_ACTIVITY)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        activity?.let {
            Logger.d("${it::class.java.simpleName} onSaveInstanceState ${it.hashCode()}", TAG_ACTIVITY)
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
        activity?.let {
            Logger.d("${it::class.java.simpleName} onDestroy ${it.hashCode()}", TAG_ACTIVITY)
        }
    }
}
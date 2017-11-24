package org.lynxz.lifecyclerobserver

import android.app.Activity
import android.app.Application
import android.os.Bundle
import org.lynxz.lifecyclerobserver.bean.ActivityInfo
import org.lynxz.lifecyclerobserver.bean.ActivityState

/**
 * Created by lynxz on 25/10/2017.
 * 管理所有的activity生命周期,打印日志信息,绘制task堆栈树
 */
object ActivityLifecycleManager : Application.ActivityLifecycleCallbacks {
    private val TAG_ACTIVITY = "activity_life_cycle"
    private var frontCount = 0

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        activity?.let {
            Logger.d("${it::class.java.simpleName} onCreate ${it.hashCode()}, taskId = ${it.taskId}", TAG_ACTIVITY)
//            RxPermissions(it).request(Manifest.permission.SYSTEM_ALERT_WINDOW)
//                    .subscribe {
//                        it.no { activity.showToast("本应用需要绘制悬浮窗权限") }
//                    }
            TaskTreeManager.instance.updateTask(getActivityInfo(activity, ActivityState.StateCreate))
        }
    }

    override fun onActivityStarted(activity: Activity?) {
        activity?.let {
            Logger.d("${it::class.java.simpleName} onStart ${it.hashCode()}", TAG_ACTIVITY)
            TaskTreeManager.instance.updateTask(getActivityInfo(activity, ActivityState.StateStart))
        }
    }

    override fun onActivityResumed(activity: Activity?) {
        activity?.let {
            frontCount += 1
            Logger.d("${it::class.java.simpleName} onResume ${it.hashCode()}", TAG_ACTIVITY)
            TaskTreeManager.instance.updateTask(getActivityInfo(activity, ActivityState.StateResume))
        }
    }

    override fun onActivityPaused(activity: Activity?) {
        activity?.let {
            Logger.d("${it::class.java.simpleName} onPause ${it.hashCode()}", TAG_ACTIVITY)
            TaskTreeManager.instance.updateTask(getActivityInfo(activity, ActivityState.StatePause))
        }
    }

    override fun onActivityStopped(activity: Activity?) {
        activity?.let {
            frontCount -= 1
            Logger.d("${it::class.java.simpleName} onStop ${it.hashCode()} $frontCount", TAG_ACTIVITY)
            TaskTreeManager.instance.updateTask(getActivityInfo(activity, ActivityState.StateStop))
            if (frontCount <= 0) {
                TaskTreeManager.instance.hide()
            }
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        activity?.let {
            Logger.d("${it::class.java.simpleName} onSaveInstanceState ${it.hashCode()}", TAG_ACTIVITY)
            TaskTreeManager.instance.updateTask(getActivityInfo(activity, ActivityState.StateSaveInstanceState))
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
        activity?.let {
            Logger.d("${it::class.java.simpleName} onDestroy ${it.hashCode()}, taskId = ${it.taskId}", TAG_ACTIVITY)
            TaskTreeManager.instance.updateTask(getActivityInfo(activity, ActivityState.StateDestroy))
            if (frontCount <= 0) {
                TaskTreeManager.instance.hide()
            }
        }
    }

    private fun getActivityInfo(act: Activity, state: ActivityState) = ActivityInfo(act, act.taskId, act::class.java.simpleName, act.hashCode(), state)

}
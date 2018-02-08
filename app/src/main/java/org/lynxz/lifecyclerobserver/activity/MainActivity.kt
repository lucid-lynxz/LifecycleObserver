package org.lynxz.lifecyclerobserver.activity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import org.lynxz.lifecyclerobserver.Logger
import org.lynxz.lifecyclerobserver.R
import org.lynxz.lifecyclerobserver.base.BaseActivity


class MainActivity : BaseActivity() {
    override fun getLayoutRes() = R.layout.activity_main

    override fun afterCreate() {
        tv_launch_mode.setOnClickListener {
            startActivity(Intent(this@MainActivity, StandardAActivity::class.java))
//            finish()
        }

        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningTaskInfoList = am.getRunningTasks(10)
        for (runningTaskInfo in runningTaskInfoList) {
            Logger.d("id: " + runningTaskInfo.id)
            Logger.d("description: " + runningTaskInfo.description)
            Logger.d("number of activities: " + runningTaskInfo.numActivities)
            Logger.d("topActivity: " + runningTaskInfo.topActivity)
            Logger.d("baseActivity: " + runningTaskInfo.baseActivity.toString())
        }
    }
}

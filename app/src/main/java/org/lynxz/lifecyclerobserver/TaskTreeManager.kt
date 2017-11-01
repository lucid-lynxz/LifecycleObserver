package org.lynxz.lifecyclerobserver

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import org.lynxz.lifecyclerobserver.activity.ShadowActivity
import org.lynxz.lifecyclerobserver.bean.ActivityInfo
import org.lynxz.lifecyclerobserver.view.TaskTreeView

/**
 * Created by lynxz on 01/11/2017.
 * 管理task堆栈信息
 */
object TaskTreeManager {
    private var mWindowManager: WindowManager? = null
    private val taskMap = mutableMapOf<Int, TaskTreeView>()

    fun init(app: Application) {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(app)) {
            val intent = Intent(app, ShadowActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            app.startActivity(intent)
        } else {
            mWindowManager = mWindowManager ?: app.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        }

    }

    @Synchronized
    fun updateTask(actInfo: ActivityInfo) {

        if (mWindowManager == null) {
            return
        }

        val key = actInfo.taskId
        taskMap.containsKey(key)
                .yes {
                    taskMap[key]?.update(actInfo)
                }
                .otherwise {
                    val taskTree = TaskTreeView(actInfo.activity)
                    taskMap.put(key, taskTree)
                    addView(taskTree, WindowManager.LayoutParams().apply {
                        gravity = Gravity.TOP or Gravity.START
                        x = 0
                        y = 100
                        type = WindowManager.LayoutParams.TYPE_PHONE
                        format = PixelFormat.RGBA_8888
                        flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        width = WindowManager.LayoutParams.WRAP_CONTENT
                        height = WindowManager.LayoutParams.WRAP_CONTENT
                    })
                    taskTree.update(actInfo)
                }
    }

    /**
     * 添加新task到悬浮窗中
     * */
    private fun addView(view: View, params: WindowManager.LayoutParams) {
        mWindowManager?.addView(view, params)
    }


}
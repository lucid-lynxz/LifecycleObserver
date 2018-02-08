package org.lynxz.lifecyclerobserver

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import org.lynxz.lifecyclerobserver.activity.ShadowActivity
import org.lynxz.lifecyclerobserver.bean.ActivityInfo
import org.lynxz.lifecyclerobserver.view.TaskTreeView

/**
 * Created by lynxz on 01/11/2017.
 * 管理task堆栈信息
 */
class TaskTreeManager private constructor() {
    private var mWindowManager: WindowManager? = null
    private val taskMap = mutableMapOf<Int, TaskTreeView>()
    private var containerView: FrameLayout? = null
    private var lineLayoutContainer: LinearLayout? = null

    companion object {
        val instance by lazy { TaskTreeManager() }
    }

    fun init(app: Application) {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(app)) {
            val intent = Intent(app, ShadowActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            app.startActivity(intent)
        } else {
            mWindowManager = mWindowManager ?: app.getSystemService(Context.WINDOW_SERVICE) as WindowManager?

            lineLayoutContainer = LinearLayout(app).apply {
                orientation = LinearLayout.HORIZONTAL
                setBackgroundColor(Color.LTGRAY)
//                Logger.d("containerViewToken =  $windowToken")
            }
            containerView = HorizontalScrollView(app).apply { addView(lineLayoutContainer) }

            mWindowManager?.addView(containerView, WindowManager.LayoutParams().apply {
                gravity = Gravity.TOP or Gravity.START
                x = 0
                y = app.resources.displayMetrics.heightPixels

                type = if (Build.VERSION.SDK_INT >= 26) {
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//                    WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL
                } else {
                    // 26以上会崩溃, 提示: permission denied for window type 2002
                    WindowManager.LayoutParams.TYPE_PHONE
                }
                format = PixelFormat.RGBA_8888
                flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                width = WindowManager.LayoutParams.WRAP_CONTENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
            })
        }
    }

    fun show() {
        containerView?.visibility = View.VISIBLE
    }

    fun hide() {
        containerView?.visibility = View.GONE
    }

    @Synchronized
    fun updateTask(actInfo: ActivityInfo) {
        if (mWindowManager == null) {
            return
        }

        show()
        val key = actInfo.taskId
        taskMap.containsKey(key)
                .yes {
                    val taskTreeView = taskMap[key]
                    taskTreeView?.update(actInfo)
                    taskTreeView?.isEmpty()?.yes {
                        removeView(taskTreeView)
                    }
                }
                .otherwise {
                    val taskTree = TaskTreeView(actInfo.activity)
                    taskMap.put(key, taskTree)
                    addView(taskTree)
                    taskTree.update(actInfo)
                }
    }

    /**
     * 添加新task到悬浮窗中
     * */
    private fun addView(view: View) {
        lineLayoutContainer?.addView(view,
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    setMargins(10, 2, 10, 2)
                })
    }

    private fun removeView(view: View?) {
        lineLayoutContainer?.removeView(view)
    }

}
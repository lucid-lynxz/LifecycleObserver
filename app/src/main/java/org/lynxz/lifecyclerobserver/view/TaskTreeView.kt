package org.lynxz.lifecyclerobserver.view

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import org.lynxz.lifecyclerobserver.R
import org.lynxz.lifecyclerobserver.adapter.RvTaskTreeAdapter
import org.lynxz.lifecyclerobserver.bean.ActivityInfo
import org.lynxz.lifecyclerobserver.bean.ActivityState
import org.lynxz.lifecyclerobserver.otherwise
import org.lynxz.lifecyclerobserver.yes

/**
 * Created by lynxz on 01/11/2017.
 * 每个taskId对应一个列表
 */
class TaskTreeView(cxt: Context) : FrameLayout(cxt) {
    private var rvTask: RecyclerView

    val data = mutableListOf<ActivityInfo>()
    private val activityCodeMap = mutableMapOf<Int, ActivityInfo>()
    private val mAdapter: RvTaskTreeAdapter by lazy {
        RvTaskTreeAdapter(cxt, data)
    }

    init {
        setBackgroundColor(Color.parseColor("#5500ff00"))
        val taskTreeView = LayoutInflater.from(cxt.applicationContext).inflate(R.layout.view_task_tree, this)
        rvTask = taskTreeView.findViewById<View>(R.id.rv_task) as RecyclerView
        rvTask.apply {
            layoutManager = LinearLayoutManager(cxt).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            adapter = mAdapter
        }
    }

    fun isEmpty() = mAdapter.data.size == 0

    fun update(act: ActivityInfo) {
        val key = act.hashCode
        activityCodeMap
                .contains(key)
                .yes {
                    activityCodeMap[key]?.state = act.state
                    if (act.state == ActivityState.StateDestroy) {
                        activityCodeMap.remove(key)
                        val newData = mutableListOf<ActivityInfo>()
                        mAdapter.data.filter { it.hashCode != key }.forEach { newData.add(it) }
                        mAdapter.data = newData
                    }
                }
                .otherwise {
                    activityCodeMap.put(key, act)
                    mAdapter.data.add(act)
                }
        mAdapter.notifyDataSetChanged()
    }


}
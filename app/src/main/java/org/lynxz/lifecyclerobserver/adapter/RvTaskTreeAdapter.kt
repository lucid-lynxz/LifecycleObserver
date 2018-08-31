package org.lynxz.lifecyclerobserver.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.lynxz.lifecyclerobserver.R
import org.lynxz.lifecyclerobserver.bean.ActivityInfo

/**
 * Created by lynxz on 01/11/2017.
 */
class RvTaskTreeAdapter(cxt: Context, var data: MutableList<ActivityInfo>) : RecyclerView.Adapter<RvTaskTreeAdapter.RvTTHolder>() {
    private val mLayoutInflater: LayoutInflater by lazy {
        LayoutInflater.from(cxt)
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RvTTHolder(mLayoutInflater.inflate(R.layout.item_rv_task_tree, parent, false))

    override fun onBindViewHolder(holder: RvTTHolder, position: Int) {
        val activityInfo = data[position]
        holder.tvItem?.apply {
            text = activityInfo.name
            setTextColor(activityInfo.state.color)
        }
    }

    inner class RvTTHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItem: TextView? = null

        init {
            tvItem = itemView.findViewById<View>(R.id.tv_item) as TextView
        }
    }
}
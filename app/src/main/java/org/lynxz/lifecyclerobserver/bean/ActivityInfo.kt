package org.lynxz.lifecyclerobserver.bean

import android.app.Activity

/**
 * Created by lynxz on 01/11/2017.
 */
data class ActivityInfo(val activity: Activity, val taskId: Int, val name: String, val hashCode: Int, var state: ActivityState)
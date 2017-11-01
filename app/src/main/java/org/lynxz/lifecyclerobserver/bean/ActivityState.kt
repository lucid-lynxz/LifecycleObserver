package org.lynxz.lifecyclerobserver.bean

import android.graphics.Color

/**
 * Created by lynxz on 01/11/2017.
 * activity各个状态对应的显示颜色
 */
sealed class ActivityState(val color: Int) {
    object StateCreate : ActivityState(Color.GREEN)
    object StateStart : ActivityState(Color.YELLOW)
    object StateResume : ActivityState(Color.RED)

    object StatePause : ActivityState(Color.WHITE)
    object StateStop : ActivityState(Color.GRAY)
    object StateSaveInstanceState : ActivityState(Color.BLUE)
    object StateDestroy : ActivityState(Color.BLACK)
}
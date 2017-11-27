package org.lynxz.lifecyclerobserver.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.lynxz.lifecyclerobserver.Logger
import org.lynxz.lifecyclerobserver.TaskTreeManager

/**
 * Created by lynxz on 24/11/2017.
 */
class KeyEventReceiver : BroadcastReceiver() {
    private val TAG = "KeyEventReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {
        Logger.d("KeyEventReceiver ${intent?.action}", TAG)
        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS == intent?.action) {
            val reason = intent.getStringExtra("reason")
            Logger.d("KeyEventReceiver reason: $reason", TAG)
            when (reason) {
                "homekey", "recentapps" -> {
                    TaskTreeManager.instance.hide()
                }
                "dream" -> { //电源键,nexus6p 8.0,其他博文说是 "lock"
                }
                "assist" -> {
                }
            }
        }
    }
}
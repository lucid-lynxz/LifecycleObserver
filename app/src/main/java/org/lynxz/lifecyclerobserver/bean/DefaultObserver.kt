package org.lynxz.lifecyclerobserver.bean

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by lynxz on 26/10/2017.
 */
open class DefaultObserver<T> : Observer<T> {
    override fun onError(e: Throwable) {
        onFinish()
        e.printStackTrace()
    }

    override fun onComplete() {
        onFinish()
    }

    override fun onNext(t: T) {
    }

    override fun onSubscribe(d: Disposable) {

    }

    open fun onFinish() {

    }
}
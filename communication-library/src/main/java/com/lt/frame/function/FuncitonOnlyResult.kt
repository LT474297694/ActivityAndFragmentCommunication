package com.lt.frame.function

import com.lt.frame.Function

/**
 * Created by L.T on 2018/3/22.
 * qq:474297694
 * 功能: 只有返回值的Function
 */
abstract class FuncitonOnlyResult<out R> (funcName: String?) : Function(funcName) {
    abstract fun function(): R?
}
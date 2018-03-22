package com.lt.frame.function

import com.lt.frame.Function

/**
 * Created by L.T on 2018/3/22.
 * qq:474297694
 * 功能: 只有参数的Function
 */
abstract class FuncitonOnlyParameter<in T>(funcName: String?) : Function(funcName) {
    abstract fun function(t: T?)
}
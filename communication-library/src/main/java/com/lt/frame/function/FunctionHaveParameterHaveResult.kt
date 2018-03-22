package com.lt.frame.function

import com.lt.frame.Function

/**
 * Created by L.T on 2018/3/22.
 * qq:474297694
 * 功能: 有参有返回值的Function
 */
abstract class FunctionHaveParameterHaveResult<in T, out R> (funcName: String?) : Function(funcName) {
    abstract fun function(t: T?): R?
}